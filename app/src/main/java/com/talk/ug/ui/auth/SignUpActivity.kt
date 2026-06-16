package com.talk.ug.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.talk.ug.data.repository.AuthRepository
import com.talk.ug.ui.main.MainActivity
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_signup)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        // nameInput = findViewById(R.id.name_input)
        // emailInput = findViewById(R.id.email_input)
        // phoneInput = findViewById(R.id.phone_input)
        // passwordInput = findViewById(R.id.password_input)
        // confirmPasswordInput = findViewById(R.id.confirm_password_input)
        // signUpButton = findViewById(R.id.signup_button)
        // loginButton = findViewById(R.id.login_button)
        // progressBar = findViewById(R.id.progress_bar)
    }

    private fun setupListeners() {
        // signUpButton.setOnClickListener {
        //     performSignUp()
        // }
        // loginButton.setOnClickListener {
        //     onBackPressed()
        // }
    }

    private fun performSignUp() {
        val name = "" // nameInput.text.toString().trim()
        val email = "" // emailInput.text.toString().trim()
        val phone = "" // phoneInput.text.toString().trim()
        val password = "" // passwordInput.text.toString().trim()
        val confirmPassword = "" // confirmPasswordInput.text.toString().trim()

        when {
            name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() -> {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            password != confirmPassword -> {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            }
            else -> {
                performSignUpRequest(email, password, name, phone)
            }
        }
    }

    private fun performSignUpRequest(email: String, password: String, name: String, phone: String) {
        // progressBar.visibility = android.view.View.VISIBLE
        lifecycleScope.launch {
            authRepository.signUp(email, password, name, phone).collect { result ->
                // progressBar.visibility = android.view.View.GONE
                result.onSuccess {
                    Toast.makeText(this@SignUpActivity, "Sign up successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finish()
                }
                result.onFailure { exception ->
                    Toast.makeText(this@SignUpActivity, "Sign up failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
