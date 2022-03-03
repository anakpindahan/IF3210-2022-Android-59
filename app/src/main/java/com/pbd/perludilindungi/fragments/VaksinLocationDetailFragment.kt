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
import kotlinx.coroutines.*


class VaksinLocationDetailFragment : Fragment() {

    private val db by lazy { BookmarkDB(requireActivity()) }

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

            val bookmarkButton = view.findViewById<Button>(R.id.buttonBookMark)

            // Set bookmark icon for the first time
            setBookmarkIcon(dataFaskes, bookmarkButton)
            // Bookmark
            bookmarkButton.setOnClickListener {
                updateBookmark(dataFaskes, bookmarkButton)
            }
        }
    }

    private fun setBookmarkIcon(dataFaskes: Data?, bookmarkButton: Button) = CoroutineScope(Dispatchers.IO).launch {
        val bookmark: Bookmark? =
            db.bookmarkDao().getBookmarkByFaskesId(dataFaskes!!.id)
        if (bookmark == null) {
            withContext(Dispatchers.Main) {
                bookmarkButton.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_add,
                    0,
                    0,
                    0
                )
            }
        } else {
            withContext(Dispatchers.Main) {
                bookmarkButton.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_remove,
                    0,
                    0,
                    0
                )
            }
        }
    }
//    }    private fun setBookmarkIcon(dataFaskes: Data?, bookmarkButton: Button) = GlobalScope.launch(Dispatchers.Main) {
//        val bookmark: Bookmark? =
//            db.bookmarkDao().getBookmarkByFaskesId(dataFaskes!!.id)
//        if (bookmark == null) {
//            Log.d("SETBOOKMARK", "at if")
//            withContext(Dispatchers.Main) {
//                bookmarkButton.setCompoundDrawablesWithIntrinsicBounds(
//                    R.drawable.ic_add,
//                    0,
//                    0,
//                    0
//                )
//            }
//        } else {
//            Log.d("SETBOOKMARK", "here")
//            withContext(Dispatchers.Main) {
//                bookmarkButton.setCompoundDrawablesWithIntrinsicBounds(
//                    R.drawable.ic_remove,
//                    0,
//                    0,
//                    0
//                )
//            }
//        }
//    }

    private fun updateBookmark(
        dataFaskes: Data?, bookmarkButton
        : Button
    ) = CoroutineScope(Dispatchers.IO).launch {
        val bookmark: Bookmark? =
            db.bookmarkDao().getBookmarkByFaskesId(dataFaskes!!.id)
        if (bookmark == null) {
            // Insert data to database
            db.bookmarkDao().addBookmark(
                Bookmark(
                    0,
                    dataFaskes.id,
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
                )
            )

            withContext(Dispatchers.Main) {
                bookmarkButton.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_remove,
                    0,
                    0,
                    0
                )
            }
        } else {
            db.bookmarkDao().deleteBookmark(bookmark!!)
            withContext(Dispatchers.Main) {
                bookmarkButton.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_add,
                    0,
                    0,
                    0
                )
            }
        }
    }


    private fun openGoogleMaps(latitute: String?, longitude: String?) {
        // Creates an Intent that will load a map of Faskes
        val gmmIntentUri = Uri.parse("geo:${latitute},${longitude}?z=20")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}

