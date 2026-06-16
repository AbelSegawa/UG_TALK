package com.talk.ug.ui.chat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var attachButton: ImageButton
    private lateinit var voiceButton: ImageButton
    private lateinit var contactNameText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_chat)

        initializeViews()
        setupRecyclerView()
        setupListeners()
    }

    private fun initializeViews() {
        // messagesRecyclerView = findViewById(R.id.messages_recycler_view)
        // messageInput = findViewById(R.id.message_input)
        // sendButton = findViewById(R.id.send_button)
        // attachButton = findViewById(R.id.attach_button)
        // voiceButton = findViewById(R.id.voice_button)
        // contactNameText = findViewById(R.id.contact_name)
    }

    private fun setupRecyclerView() {
        // messagesRecyclerView.layoutManager = LinearLayoutManager(this).apply {
        //     stackFromEnd = true
        // }
    }

    private fun setupListeners() {
        // sendButton.setOnClickListener {
        //     val message = messageInput.text.toString().trim()
        //     if (message.isNotEmpty()) {
        //         // Send message
        //         messageInput.text.clear()
        //     }
        // }
        // attachButton.setOnClickListener {
        //     // Open file picker for images
        // }
        // voiceButton.setOnClickListener {
        //     // Start voice recording
        // }
    }
}
