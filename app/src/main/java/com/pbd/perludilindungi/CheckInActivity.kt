package com.pbd.perludilindungi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.pbd.perludilindungi.databinding.ActivityCheckInBinding
import com.pbd.perludilindungi.model.QrCodeResponseModel
import com.pbd.perludilindungi.services.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckInActivity : AppCompatActivity(), LocationListener, SensorEventListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private lateinit var binding: ActivityCheckInBinding
    private lateinit var sensorManager: SensorManager

    companion object {
        var latitude = 0.0
        var longitude = 0.0
    }

    private var temperature: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLocation()
        val scanButton: Button = findViewById(R.id.scan_button)
        scanButton.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this).apply {
                setPrompt("Scan a qr code PerluDilindungi")
                setCameraId(0)
                setOrientationLocked(true)
                captureActivity = CaptureActivityPortrait::class.java
            }
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        }

        // Sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (temperature == null) {
            binding.temperatureValue.text = "No sensor"
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                val valueQr = result.contents
                postQrCodeData(valueQr, latitude, longitude)
            }
        }
    }

    private fun postQrCodeData(valueQr: String, latitude: Double, longitude: Double) {
        ApiService.endpoint.postQrCode(valueQr, latitude, longitude)
            .enqueue(object : Callback<QrCodeResponseModel> {
                override fun onResponse(
                    call: Call<QrCodeResponseModel>,
                    response: Response<QrCodeResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        if (responseData != null) {
                            if (responseData.success == "true") {
                                val responseMsg : String
                                var reasonMsg = ""
                                if (responseData.data.userStatus == "green" || responseData.data.userStatus == "yellow") {
                                    responseMsg = "Berhasil"
                                } else {
                                    responseMsg = "Gagal"
                                    reasonMsg = responseData.data.reason
                                }

                                when (responseData.data.userStatus) {
                                    "green" -> binding.imageStatusTrue.setImageResource(R.drawable.green_check)
                                    "yellow" -> binding.imageStatusTrue.setImageResource(R.drawable.yellow)
                                    "red" -> binding.imageStatusTrue.setImageResource(R.drawable.red_cross)
                                    "black" -> binding.imageStatusTrue.setImageResource(R.drawable.black)
                                }
                                binding.imageStatusTrue.visibility = View.VISIBLE
                                binding.statusText.text = responseMsg
                                binding.reason.text = reasonMsg
                            } else {
                                binding.statusText.text = responseData.message ?: "Gagal scan"
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<QrCodeResponseModel>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Qrcode scan unidentified ...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            val tempValue = p0.values[0]
            when {
                tempValue > 40.0 -> {
                    binding.temperatureValue.setTextColor(resources.getColor(R.color.hot))
                }
                tempValue < 20.0 -> {
                    binding.temperatureValue.setTextColor(resources.getColor(R.color.cool))
                }
                else -> {
                    binding.temperatureValue.setTextColor(resources.getColor(R.color.warm))
                }
            }
            binding.temperatureValue.text =
                resources.getString(R.string.temperature_val, tempValue.toString())
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        if (p1 == SensorManager.SENSOR_STATUS_ACCURACY_LOW) {
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