package com.example.kutira_kushala

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class RoleSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)

        findViewById<View>(R.id.cv_seller).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        findViewById<View>(R.id.cv_buyer).setOnClickListener {
            startActivity(Intent(this, BuyerRegistrationActivity::class.java))
        }

        findViewById<View>(R.id.tv_sign_in).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
