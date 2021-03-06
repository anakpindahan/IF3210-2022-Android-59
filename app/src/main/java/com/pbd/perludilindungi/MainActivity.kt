package com.pbd.perludilindungi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pbd.perludilindungi.fragments.BookmarkFragment
import com.pbd.perludilindungi.fragments.NewsFragment
import com.pbd.perludilindungi.fragments.VaksinLocationFragment

class MainActivity : AppCompatActivity() {
    private val newsFragment = NewsFragment()
    private val bookmarkFragment = BookmarkFragment()
    private val vaksinLocationFragment = VaksinLocationFragment()

    lateinit var bottomNavView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PerluDilindungi)
        setContentView(R.layout.activity_main)
        replaceFragment(newsFragment)

        bottomNavView = findViewById(R.id.bottom_nav)
        bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_news -> replaceFragment(newsFragment)
                R.id.ic_location -> replaceFragment(vaksinLocationFragment)
                R.id.ic_bookmark -> replaceFragment(bookmarkFragment)
            }
            true
        }

        val qrCodeButton : ImageButton = findViewById(R.id.qrcode_button)
        qrCodeButton.setOnClickListener{
            val intent = Intent(this, CheckInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment: Fragment?){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}