package com.pbd.perludilindungi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pbd.perludilindungi.fragments.BookmarkFragment
import com.pbd.perludilindungi.fragments.NewsFragment
import com.pbd.perludilindungi.fragments.VaksinLocationFragment
import com.pbd.perludilindungi.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val newsFragment = NewsFragment()
    private val bookmarkFragment = BookmarkFragment()
    private val vaksinLocationFragment = VaksinLocationFragment()
    private val TAG: String = "NewsFragment"

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
        Log.d(TAG, "HALOOO")
    }

    private fun replaceFragment(fragment: Fragment?){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}