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

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var etFullName: EditText
    private lateinit var etBusinessName: EditText
    private lateinit var etMobile: EditText
    private lateinit var etCategory: EditText
    private lateinit var etLocation: EditText
    private lateinit var etPasscode: EditText
    private lateinit var btnRegister: Button

    private val categories = arrayOf("Crafts & Handicrafts", "Food & Processed Items", "Textiles & Handloom", "Handmade Decor", "Agarbatti & Spices")

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        etFullName = findViewById(R.id.et_full_name)
        etBusinessName = findViewById(R.id.et_business_name)
        etMobile = findViewById(R.id.et_mobile)
        etCategory = findViewById(R.id.et_category)
        etLocation = findViewById(R.id.et_location)
        etPasscode = findViewById(R.id.et_passcode)
        btnRegister = findViewById(R.id.btn_register)

        findViewById<View>(R.id.btn_back).setOnClickListener { handleBackNavigation() }
        etCategory.setOnClickListener { showCategoryDialog() }

        btnRegister.setOnClickListener {
            if (performValidation()) {
                registerUser()
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { handleBackNavigation() }
        })
    }

    private fun showCategoryDialog() {
        AlertDialog.Builder(this)
            .setTitle("Select Product Category")
            .setItems(categories) { _, which -> etCategory.setText(categories[which]) }
            .show()
    }

    private fun performValidation(): Boolean {
        val name = etFullName.text.toString().trim()
        val business = etBusinessName.text.toString().trim()
        val mobile = etMobile.text.toString().trim()
        val category = etCategory.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val password = etPasscode.text.toString()

        if (name.isEmpty()) { etFullName.error = "Enter full name"; return false }
        if (business.isEmpty()) { etBusinessName.error = "Enter business name"; return false }
        if (mobile.length != 10 || !mobile.all { it.isDigit() }) { etMobile.error = "Enter valid 10-digit mobile number"; return false }
        if (category.isEmpty()) { Toast.makeText(this, "Select a product category", Toast.LENGTH_SHORT).show(); return false }
        if (location.isEmpty()) { etLocation.error = "Enter business location"; return false }
        if (password.length < 6) { etPasscode.error = "Password must be at least 6 characters"; return false }

        return true
    }

    private fun registerUser() {
        val name = etFullName.text.toString().trim()
        val business = etBusinessName.text.toString().trim()
        val mobile = etMobile.text.toString().trim()
        val category = etCategory.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val password = etPasscode.text.toString()
        
        val tempEmail = "$mobile@kutira.com"

        btnRegister.isEnabled = false
        
        auth.createUserWithEmailAndPassword(tempEmail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        saveUserAndSeller(uid, name, business, mobile, category, location)
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

    private fun saveUserAndSeller(uid: String, name: String, business: String, mobile: String, category: String, location: String) {
        val batch = firestore.batch()
        
        val userRef = firestore.collection("users").document(uid)
        val userMap = hashMapOf(
            "uid" to uid,
            "fullName" to name,
            "mobileNumber" to mobile,
            "createdAt" to FieldValue.serverTimestamp()
        )
        batch.set(userRef, userMap)

        val sellerRef = firestore.collection("sellers").document(uid)
        val sellerMap = hashMapOf(
            "sellerId" to uid,
            "sellerName" to name,
            "businessName" to business,
            "mobileNumber" to mobile,
            "skills" to listOf(category),
            "location" to location,
            "experienceYears" to 0,
            "rating" to 5.0f,
            "productsCount" to 0,
            "createdAt" to FieldValue.serverTimestamp()
        )
        batch.set(sellerRef, sellerMap)

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
