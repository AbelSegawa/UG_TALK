package com.talk.ug.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talk.ug.ui.main.fragment.ChatsFragment
import com.talk.ug.ui.main.fragment.ContactsFragment
import com.talk.ug.ui.main.fragment.ProfileFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ChatsFragment()
            1 -> ContactsFragment()
            2 -> ProfileFragment()
            else -> ChatsFragment()
        }
    }
}
