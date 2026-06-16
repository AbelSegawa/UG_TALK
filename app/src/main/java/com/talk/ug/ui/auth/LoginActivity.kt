package com.talk.ug.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.talk.ug.data.repository.AuthRepository
import com.talk.ug.ui.main.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var progressBar: ProgressBar
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_login)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        // These will be properly initialized when layout is created
        // emailInput = findViewById(R.id.email_input)
        // passwordInput = findViewById(R.id.password_input)
        // loginButton = findViewById(R.id.login_button)
        // signUpButton = findViewById(R.id.signup_button)
        // progressBar = findViewById(R.id.progress_bar)
    }

    private fun setupListeners() {
        // loginButton.setOnClickListener {
        //     performLogin()
        // }
        // signUpButton.setOnClickListener {
        //     startActivity(Intent(this, SignUpActivity::class.java))
        // }
    }

    private fun performLogin() {
        val email = "" // emailInput.text.toString().trim()
        val password = "" // passwordInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // progressBar.visibility = android.view.View.VISIBLE
        lifecycleScope.launch {
            authRepository.login(email, password).collect { result ->
                // progressBar.visibility = android.view.View.GONE
                result.onSuccess {
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                result.onFailure { exception ->
                    Toast.makeText(this@LoginActivity, "Login failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
