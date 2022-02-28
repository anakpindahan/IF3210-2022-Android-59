package com.pbd.perludilindungi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            System.out.println("Hi from onView Created vaksin location detail, there is argument")
            var dataFaskes = arguments?.getParcelable<Data>("EXTRA_FASKES")
            System.out.println(dataFaskes?.jenis_faskes)
            var nameFaskes = view.findViewById(R.id.faskes_name) as TextView
            nameFaskes.text = dataFaskes?.nama
            var codeFaskes = view.findViewById(R.id.faskes_code) as TextView
            codeFaskes.text = dataFaskes?.kode
            var addressFaskes = view.findViewById(R.id.faskes_address) as TextView
            addressFaskes.text = dataFaskes?.alamat
            var phoneFaskes = view.findViewById(R.id.faskes_phone) as TextView
            phoneFaskes.text = dataFaskes?.telp
            var typeFaskes = view.findViewById(R.id.faskes_type) as TextView
            typeFaskes.text = dataFaskes?.jenis_faskes



        }
    }

}