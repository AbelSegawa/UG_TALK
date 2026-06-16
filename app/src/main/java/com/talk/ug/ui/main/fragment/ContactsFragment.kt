package com.talk.ug.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class ContactsFragment : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment_contacts, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // searchInput = view.findViewById(R.id.search_input)
        // recyclerView = view.findViewById(R.id.contacts_recycler_view)
        setupSearch()
    }

    private fun setupSearch() {
        // searchInput.addTextChangedListener(object : android.text.TextWatcher {
        //     override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        //     override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //         // Filter contacts based on search query
        //     }
        //     override fun afterTextChanged(s: android.text.Editable?) {}
        // })
    }
}
