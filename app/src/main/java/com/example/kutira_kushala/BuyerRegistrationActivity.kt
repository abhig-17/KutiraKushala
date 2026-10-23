package com.example.kutira_kushala

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class BuyerRegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var etFullName: EditText
    private lateinit var etCompanyName: EditText
    private lateinit var etMobile: EditText
    private lateinit var etBuyerType: EditText
    private lateinit var etLocation: EditText
    private lateinit var etPasscode: EditText
    private lateinit var btnRegister: Button

    private val buyerTypes = arrayOf("Retailer Store", "Wholesaler / Distributor", "Exporter", "Institutional Buyer", "Individual Bulk Buyer")

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_registration)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        etFullName = findViewById(R.id.et_full_name)
        etCompanyName = findViewById(R.id.et_company_name)
        etMobile = findViewById(R.id.et_mobile)
        etBuyerType = findViewById(R.id.et_buyer_type)
        etLocation = findViewById(R.id.et_location)
        etPasscode = findViewById(R.id.et_passcode)
        btnRegister = findViewById(R.id.btn_register)

        findViewById<View>(R.id.btn_back).setOnClickListener { handleBackNavigation() }
        etBuyerType.setOnClickListener { showBuyerTypeDialog() }

        btnRegister.setOnClickListener {
            if (performValidation()) {
                registerUser()
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { handleBackNavigation() }
        })
    }

    private fun showBuyerTypeDialog() {
        AlertDialog.Builder(this)
            .setTitle("Select Buyer Type")
            .setItems(buyerTypes) { _, which -> etBuyerType.setText(buyerTypes[which]) }
            .show()
    }

    private fun performValidation(): Boolean {
        val name = etFullName.text.toString().trim()
        val company = etCompanyName.text.toString().trim()
        val mobile = etMobile.text.toString().trim()
        val buyerType = etBuyerType.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val password = etPasscode.text.toString()

        if (name.isEmpty()) { etFullName.error = "Enter full name"; return false }
        if (company.isEmpty()) { etCompanyName.error = "Enter company name"; return false }
        if (mobile.length != 10 || !mobile.all { it.isDigit() }) { etMobile.error = "Enter valid 10-digit mobile number"; return false }
        if (buyerType.isEmpty()) { Toast.makeText(this, "Select a buyer type", Toast.LENGTH_SHORT).show(); return false }
        if (location.isEmpty()) { etLocation.error = "Enter business location"; return false }
        if (password.length < 6) { etPasscode.error = "Password must be at least 6 characters"; return false }

        return true
    }

    private fun registerUser() {
        val name = etFullName.text.toString().trim()
        val company = etCompanyName.text.toString().trim()
        val mobile = etMobile.text.toString().trim()
        val buyerType = etBuyerType.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val password = etPasscode.text.toString()
        
        val tempEmail = "$mobile@kutira.com"

        btnRegister.isEnabled = false
        
        auth.createUserWithEmailAndPassword(tempEmail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        saveBuyerUser(uid, name, company, mobile, buyerType, location)
                    }
                } else {
                    btnRegister.isEnabled = true
                    val message = when (task.exception) {
                        is FirebaseAuthUserCollisionException -> "Mobile number already registered"
                        is FirebaseAuthWeakPasswordException -> "Password is too weak"
                        else -> task.exception?.localizedMessage ?: "Registration failed"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveBuyerUser(uid: String, name: String, company: String, mobile: String, buyerType: String, location: String) {
        val batch = firestore.batch()
        
        val userRef = firestore.collection("users").document(uid)
        val userMap = hashMapOf(
            "uid" to uid,
            "fullName" to name,
            "companyName" to company,
            "mobileNumber" to mobile,
            "role" to "buyer",
            "buyerType" to buyerType,
            "location" to location,
            "createdAt" to FieldValue.serverTimestamp()
        )
        batch.set(userRef, userMap)

        batch.commit()
            .addOnSuccessListener {
                Toast.makeText(this, "Welcome to Kutira-Kushala!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                btnRegister.isEnabled = true
                Toast.makeText(this, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }

    private fun handleBackNavigation() {
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
