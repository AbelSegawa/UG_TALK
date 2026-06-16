package com.talk.ug.ui.profile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.talk.ug.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var profilePicture: ImageView
    private lateinit var nameInput: EditText
    private lateinit var bioInput: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_profile)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        // profilePicture = findViewById(R.id.profile_picture)
        // nameInput = findViewById(R.id.name_input)
        // bioInput = findViewById(R.id.bio_input)
        // saveButton = findViewById(R.id.save_button)
        // cancelButton = findViewById(R.id.cancel_button)
    }

    private fun setupListeners() {
        // profilePicture.setOnClickListener {
        //     // Open image picker
        // }
        // saveButton.setOnClickListener {
        //     updateProfile()
        // }
        // cancelButton.setOnClickListener {
        //     finish()
        // }
    }

    private fun updateProfile() {
        val name = "" // nameInput.text.toString().trim()
        val bio = "" // bioInput.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            userRepository.updateUserProfile(
                userId = "", // Get from auth
                name = name,
                bio = bio
            ).collect { result ->
                result.onSuccess {
                    Toast.makeText(this@ProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                result.onFailure { exception ->
                    Toast.makeText(this@ProfileActivity, "Update failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
