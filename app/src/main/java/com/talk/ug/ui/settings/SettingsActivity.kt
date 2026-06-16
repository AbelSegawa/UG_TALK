package com.talk.ug.ui.settings

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    private lateinit var notificationSwitch: Switch
    private lateinit var darkModeSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_settings)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        // notificationSwitch = findViewById(R.id.notification_switch)
        // darkModeSwitch = findViewById(R.id.dark_mode_switch)
    }

    private fun setupListeners() {
        // notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
        //     // Save notification preference
        // }
        // darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
        //     if (isChecked) {
        //         AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        //     } else {
        //         AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //     }
        // }
    }
}
