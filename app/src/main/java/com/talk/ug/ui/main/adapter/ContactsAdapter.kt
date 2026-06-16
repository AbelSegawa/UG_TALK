package com.talk.ug.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talk.ug.domain.model.User

class ContactsAdapter(
    private var contacts: List<User> = emptyList(),
    private val onContactClickListener: (User) -> Unit = {}
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        // return ContactViewHolder(view)
        return ContactViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // private val profilePic: ImageView = itemView.findViewById(R.id.profile_pic)
        // private val nameText: TextView = itemView.findViewById(R.id.name_text)
        // private val statusText: TextView = itemView.findViewById(R.id.status_text)
        // private val onlineIndicator: View = itemView.findViewById(R.id.online_indicator)

        fun bind(user: User) {
            // nameText.text = user.name
            // statusText.text = if (user.isOnline) "Online" else "Offline"
            
            // if (user.isOnline) {
            //     onlineIndicator.visibility = View.VISIBLE
            // } else {
            //     onlineIndicator.visibility = View.GONE
            // }

            // itemView.setOnClickListener {
            //     onContactClickListener(user)
            // }
        }
    }

    fun updateContacts(newContacts: List<User>) {
        contacts = newContacts
        notifyDataSetChanged()
    }
}
