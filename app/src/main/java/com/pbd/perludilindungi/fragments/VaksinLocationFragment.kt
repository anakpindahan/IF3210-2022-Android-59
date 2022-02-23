package com.pbd.perludilindungi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.pbd.perludilindungi.Place
import com.pbd.perludilindungi.ProvinceCityModel
import com.pbd.perludilindungi.R
import com.pbd.perludilindungi.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VaksinLocationFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaksin_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProvinceFromAPI()

    }
    private fun getProvinceFromAPI(){
        ApiService.endpoint.getProvince()
            .enqueue(object : Callback<ProvinceCityModel> {
                override fun onResponse(call: Call<ProvinceCityModel>, response: Response<ProvinceCityModel>) {
                    if (response.isSuccessful){
                        val results = response.body()
                        results?.let {
                            setArrayOfDropDown(it.results)
                        }
                    }
                }

                override fun onFailure(call: Call<ProvinceCityModel>, t: Throwable) {
                    Log.d("Faskes Fragment Error : ",t.toString())
                }

            })
    }

    private fun setArrayOfDropDown(PlaceList : List<Place>){
        val dropdownArray = ArrayList<String>()
        for(item in PlaceList){
            dropdownArray.add(item.value);
        }
//            lateinit var arrayAdapter: ArrayAdapter<String>
////    lateinit var autoCompleteTextView: AutoCompleteTextView
//          arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_menu, dropdownArray)
//          autoCompleteTextView.findViewById(R.id.dropdown_list_menu)

    }

}
