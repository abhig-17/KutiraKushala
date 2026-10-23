package com.example.kutira_kushala
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    private var selectedRole = "seller" // "seller" or "buyer"

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        
        auth = FirebaseAuth.getInstance()
        
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val btnBack = findViewById<View>(R.id.btn_back)
        val btnRoleSeller = findViewById<Button>(R.id.btn_role_seller)
        val btnRoleBuyer = findViewById<Button>(R.id.btn_role_buyer)
        val etMobile = findViewById<EditText>(R.id.et_mobile)
        val etPasscode = findViewById<EditText>(R.id.et_passcode)
        val tvForgot = findViewById<TextView>(R.id.tv_forgot_password)
        val btnAuthenticate = findViewById<Button>(R.id.btn_authenticate)

        btnBack.setOnClickListener { finish() }

        btnRoleSeller.setOnClickListener {
            selectedRole = "seller"
            btnRoleSeller.setBackgroundResource(R.drawable.card_bg_white)
            btnRoleSeller.setTextColor(Color.parseColor("#1A2E26"))
            btnRoleSeller.elevation = 4f

            btnRoleBuyer.setBackgroundColor(Color.TRANSPARENT)
            btnRoleBuyer.setTextColor(Color.parseColor("#64748B"))
            btnRoleBuyer.elevation = 0f
        }

        btnRoleBuyer.setOnClickListener {
            selectedRole = "buyer"
            btnRoleBuyer.setBackgroundResource(R.drawable.card_bg_white)
            btnRoleBuyer.setTextColor(Color.parseColor("#1A2E26"))
            btnRoleBuyer.elevation = 4f

            btnRoleSeller.setBackgroundColor(Color.TRANSPARENT)
            btnRoleSeller.setTextColor(Color.parseColor("#64748B"))
            btnRoleSeller.elevation = 0f
        }

        tvForgot.setOnClickListener {
            Toast.makeText(this, "Password reset instructions sent to registered mobile", Toast.LENGTH_LONG).show()
        }

        btnAuthenticate.setOnClickListener {
            val mobile = etMobile.text.toString().trim()
            val passcode = etPasscode.text.toString().trim()

            if (validate(mobile, passcode)) {
                loginUser(mobile, passcode)
            }
        }
    }

    private fun validate(mobile: String, passcode: String): Boolean {
        if (mobile.length != 10 || !mobile.all { it.isDigit() }) {
            findViewById<EditText>(R.id.et_mobile).error = "Invalid mobile number"
            return false
        }
        if (passcode.isEmpty()) {
            findViewById<EditText>(R.id.et_passcode).error = "Password required"
            return false
        }
        return true
    }

    private fun loginUser(mobile: String, passcode: String) {
        val tempEmail = "$mobile@kutira.com"
        val btnAuthenticate = findViewById<Button>(R.id.btn_authenticate)
        
        btnAuthenticate.isEnabled = false
        
        auth.signInWithEmailAndPassword(tempEmail, passcode)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Signed in successfully as $selectedRole!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {
                    btnAuthenticate.isEnabled = true
                    val exception = task.exception
                    val message = when (exception) {
                        is FirebaseAuthInvalidUserException -> "Account not found"
                        is FirebaseAuthInvalidCredentialsException -> "Incorrect password"
                        else -> exception?.localizedMessage ?: "Authentication failed"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
    }
}
