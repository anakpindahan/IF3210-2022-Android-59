package com.pbd.perludilindungi

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.pbd.perludilindungi.databinding.ActivityCheckInBinding

class CheckInActivity: AppCompatActivity(), LocationListener, SensorEventListener  {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private lateinit var binding: ActivityCheckInBinding
    private lateinit var sensorManager : SensorManager
    private var temperature: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scanButton : Button = findViewById(R.id.scan_button)
        scanButton.setOnClickListener{
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        }
        getLocation()

        // Sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

    }

    private fun getLocation(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        val text2 : TextView = binding.textView2
        val text3 : TextView = binding.textView3

        text2.text = location.latitude.toString()
        text3.text = location.longitude.toString()
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            val tempValue = p0.values[0]
            if (tempValue > 40.0){
                binding.temperatureValue.setTextColor(resources.getColor(R.color.hot))
            } else if (tempValue < 20.0) {
                binding.temperatureValue.setTextColor(resources.getColor(R.color.cool))
            } else {
                binding.temperatureValue.setTextColor(resources.getColor(R.color.warm))
            }
            binding.temperatureValue.text = resources.getString(R.string.temperature_val, tempValue.toString())
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        if (p1 == SensorManager.SENSOR_STATUS_ACCURACY_LOW){
            binding.temperatureValue.text = resources.getString(R.string.calibrating)
        }
    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}