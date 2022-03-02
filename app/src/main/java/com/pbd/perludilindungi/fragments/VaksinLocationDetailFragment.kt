package com.pbd.perludilindungi.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pbd.perludilindungi.Data
import com.pbd.perludilindungi.R


class VaksinLocationDetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaksin_location_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null){
            val dataFaskes = arguments?.getParcelable<Data>("EXTRA_FASKES")
            val nameFaskes = view.findViewById(R.id.faskes_name) as TextView
            nameFaskes.text = dataFaskes?.nama
            val codeFaskes = view.findViewById(R.id.faskes_code) as TextView
            codeFaskes.text = dataFaskes?.kode
            val addressFaskes = view.findViewById(R.id.faskes_address) as TextView
            addressFaskes.text = dataFaskes?.alamat
            val phoneFaskes = view.findViewById(R.id.faskes_phone) as TextView
            phoneFaskes.text = dataFaskes?.telp
            val typeFaskes = view.findViewById(R.id.faskes_type) as TextView
            typeFaskes.text = dataFaskes?.jenis_faskes
            if(dataFaskes?.status.equals("Siap Vaksinasi")){
                val imageFaskesTrue = view.findViewById(R.id.image_status_true) as ImageView
                imageFaskesTrue.visibility = View.VISIBLE
            }else{
                val imageFaskesFalse = view.findViewById(R.id.image_status_false) as ImageView
                imageFaskesFalse.visibility = View.VISIBLE
            }
            val statusFaskes = view.findViewById(R.id.faskes_status) as TextView
            statusFaskes.text = dataFaskes?.status
            val googleMapsButton = view.findViewById(R.id.buttonGoogleMaps) as Button
            googleMapsButton.setOnClickListener {
                openGoogleMaps(dataFaskes?.latitude,dataFaskes?.longitude)
            }

        }
    }

    private fun openGoogleMaps(latitute : String?, longitude : String?){
        // Creates an Intent that will load a map of San Francisco
        val gmmIntentUri = Uri.parse("geo:${latitute},${longitude}?z=20")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}