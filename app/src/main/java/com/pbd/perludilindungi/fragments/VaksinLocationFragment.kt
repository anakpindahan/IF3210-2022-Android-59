package com.pbd.perludilindungi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.pbd.perludilindungi.Place
import com.pbd.perludilindungi.ProvinceCityModel
import com.pbd.perludilindungi.R
import com.pbd.perludilindungi.databinding.FragmentVaksinLocationBinding
import com.pbd.perludilindungi.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VaksinLocationFragment : Fragment() {
    private var _binding : FragmentVaksinLocationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVaksinLocationBinding.inflate(inflater,container,false)

        return binding.root
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
                            setArrayOfProvinceDropDown(it.results)
                        }
                    }
                }

                override fun onFailure(call: Call<ProvinceCityModel>, t: Throwable) {
                    Log.d("Faskes Fragment Error : ",t.toString())
                }

            })
    }

    private fun getCityFromAPI(){
        ApiService.endpoint.getProvince()
            .enqueue(object : Callback<ProvinceCityModel> {
                override fun onResponse(call: Call<ProvinceCityModel>, response: Response<ProvinceCityModel>) {
                    if (response.isSuccessful){
                        val results = response.body()
                        results?.let {
                            setArrayOfProvinceDropDown(it.results)
                        }
                    }
                }

                override fun onFailure(call: Call<ProvinceCityModel>, t: Throwable) {
                    Log.d("Faskes Fragment Error : ",t.toString())
                }
            })

    }

    private fun setArrayOfProvinceDropDown(PlaceList : List<Place>) {
        val dropdownArray = ArrayList<String>()
        for (item in PlaceList) {
            dropdownArray.add(item.value)
        }
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_list_menu,
            dropdownArray
        )
        binding.ProvinceDropdownMenu?.setAdapter(adapter)
    }

    // biar ga memory leak
    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}
