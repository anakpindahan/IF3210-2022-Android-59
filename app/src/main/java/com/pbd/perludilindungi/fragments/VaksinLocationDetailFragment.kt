package com.pbd.perludilindungi.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pbd.perludilindungi.Data
import com.pbd.perludilindungi.R
import com.pbd.perludilindungi.room.Bookmark
import com.pbd.perludilindungi.room.BookmarkDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VaksinLocationDetailFragment : Fragment() {

    val db by lazy { BookmarkDB(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaksin_location_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val dataFaskes = arguments?.getParcelable<Data>("EXTRA_FASKES")
            Log.d("DETAILFRAGMENTFASKES", dataFaskes.toString())
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
            if (dataFaskes?.status.equals("Siap Vaksinasi")) {
                val imageFaskesTrue = view.findViewById(R.id.image_status_true) as ImageView
                imageFaskesTrue.visibility = View.VISIBLE
            } else {
                val imageFaskesFalse = view.findViewById(R.id.image_status_false) as ImageView
                imageFaskesFalse.visibility = View.VISIBLE
            }
            val statusFaskes = view.findViewById(R.id.faskes_status) as TextView
            statusFaskes.text = dataFaskes?.status

            // Gmaps
            val googleMapsButton = view.findViewById(R.id.buttonGoogleMaps) as Button
            googleMapsButton.setOnClickListener {
                openGoogleMaps(dataFaskes?.latitude, dataFaskes?.longitude)
            }

            // Bookmark
            val bookmarkButton = view.findViewById<Button>(R.id.buttonBookMark)
            bookmarkButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (dataFaskes != null) {
//                        Insert data to database
                        db.bookmarkDao().addBookmark(
                            Bookmark(
                                0,
                                dataFaskes.id!!,
                                dataFaskes.kode!!,
                                dataFaskes.nama!!,
                                dataFaskes.provinsi!!,
                                dataFaskes.kota!!,
                                dataFaskes.alamat!!,
                                dataFaskes.latitude!!,
                                dataFaskes.longitude!!,
                                dataFaskes.telp,
                                dataFaskes.jenis_faskes,
                                dataFaskes.kelas_rs,
                                dataFaskes.status!!,
                                dataFaskes.sourceData
                            )
                        )
                    }
//                    Look if data is added succesfully
                    val bookmarks = db.bookmarkDao().getBookmarks()
                    Log.d("DETAILLOCATION", bookmarks.toString())
                }
            }

        }
    }

    private fun openGoogleMaps(latitute: String?, longitude: String?) {
        // Creates an Intent that will load a map of San Francisco
        val gmmIntentUri = Uri.parse("geo:${latitute},${longitude}?z=20")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}