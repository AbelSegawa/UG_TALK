package com.talk.ug.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.talk.ug.ui.main.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        initializeViews()
        setupViewPager()
        setupBottomNavigation()
    }

    private fun initializeViews() {
        // viewPager = findViewById(R.id.view_pager)
        // bottomNav = findViewById(R.id.bottom_navigation)
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        // viewPager.adapter = adapter
    }

    private fun setupBottomNavigation() {
        // bottomNav.setOnItemSelectedListener { item ->
        //     when (item.itemId) {
        //         R.id.nav_chats -> viewPager.currentItem = 0
        //         R.id.nav_contacts -> viewPager.currentItem = 1
        //         R.id.nav_profile -> viewPager.currentItem = 2
        //     }
        //     true
        // }

        // viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        //     override fun onPageSelected(position: Int) {
        //         bottomNav.menu.getItem(position).isChecked = true
        //     }
        // })
    }
}
