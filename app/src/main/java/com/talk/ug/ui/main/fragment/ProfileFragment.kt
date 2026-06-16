package com.talk.ug.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var profilePicture: ImageView
    private lateinit var nameText: TextView
    private lateinit var phoneText: TextView
    private lateinit var bioText: TextView
    private lateinit var editButton: Button
    private lateinit var settingsButton: Button
    private lateinit var deleteAccountButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment_profile, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // profilePicture = view.findViewById(R.id.profile_picture)
        // nameText = view.findViewById(R.id.name_text)
        // phoneText = view.findViewById(R.id.phone_text)
        // bioText = view.findViewById(R.id.bio_text)
        // editButton = view.findViewById(R.id.edit_button)
        // settingsButton = view.findViewById(R.id.settings_button)
        // deleteAccountButton = view.findViewById(R.id.delete_account_button)
        setupListeners()
    }

    private fun setupListeners() {
        // editButton.setOnClickListener {
        //     // Navigate to edit profile
        // }
        // settingsButton.setOnClickListener {
        //     // Navigate to settings
        // }
        // deleteAccountButton.setOnClickListener {
        //     // Show delete confirmation dialog
        // }
    }
}
