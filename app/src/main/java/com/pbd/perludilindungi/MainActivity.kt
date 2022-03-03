package com.pbd.perludilindungi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pbd.perludilindungi.fragments.BookmarkFragment
import com.pbd.perludilindungi.fragments.NewsFragment
import com.pbd.perludilindungi.fragments.VaksinLocationFragment

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var  locationManager: LocationManager
    private val locationPermissionCode = 2

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
            getLocation()
        }
    }

    private fun replaceFragment(fragment: Fragment?){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    private fun getLocation(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {

    }
}