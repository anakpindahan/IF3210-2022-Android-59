package com.pbd.perludilindungi.fragments

import android.content.Intent
import android.os.Bundle
import android.os.health.SystemHealthManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pbd.perludilindungi.*
import com.pbd.perludilindungi.databinding.FragmentVaksinLocationBinding
import com.pbd.perludilindungi.services.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class VaksinLocationFragment : Fragment() {

    //atribut
    private var _binding : FragmentVaksinLocationBinding? = null
    private val binding get() = _binding!!
    lateinit var faskesAdapter: FaskesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View {
        // Inflate the layout for this fragment
        _binding = FragmentVaksinLocationBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProvinceFromAPI()
        val province_auto_complete_text_view = view.findViewById(R.id.ProvinceDropdownMenu) as AutoCompleteTextView
        province_auto_complete_text_view.setOnItemClickListener { _, _, _, _ ->
            getCityFromAPI()
        }
        val city_auto_complete_text_view = view.findViewById(R.id.CityDropdownMenu) as AutoCompleteTextView
        city_auto_complete_text_view.setOnItemClickListener { _, _, _, _ ->
            setupRecyclerView()
            getFaksesFromAPI()
        }

    }
    //function for setup recyclerview for faskes
    private fun setupRecyclerView() {
        faskesAdapter = FaskesAdapter(arrayListOf(), object : FaskesAdapter.OnAdapterListener{
            override fun onClick(result: Data) {
                val detailLocationFragment = VaksinLocationDetailFragment()
                val mbundle = Bundle()
                mbundle.putParcelable("EXTRA_FASKES", result)
                detailLocationFragment.arguments = mbundle
                val mFragmentManager = parentFragmentManager
                mFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fragment_container, detailLocationFragment, VaksinLocationDetailFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }

            }
        })
        val recyclerView: RecyclerView = requireView().findViewById(R.id.faskes_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = faskesAdapter
        }
    }


    private fun getProvinceFromAPI(){
        ApiService.endpoint.getProvince()
            .enqueue(object : Callback<ProvinceCityModel> {
                override fun onResponse(call: Call<ProvinceCityModel>, response: Response<ProvinceCityModel>) {
                    if (response.isSuccessful){
                        val results = response.body()
                        results?.let {
                            setArrayOfProvince(it.results)
                        }
                    }
                }
                override fun onFailure(call: Call<ProvinceCityModel>, t: Throwable) {
                    Log.d("Get Province Error : ",t.toString())
                }
            })
    }
    private fun getCityFromAPI(){
//        get String from Province AutoCompleteTextView
        val selectedItem =  getSelectedProvince()
        ApiService.endpoint.getCity(selectedItem)
            .enqueue(object : Callback<ProvinceCityModel> {
                override fun onResponse(call: Call<ProvinceCityModel>, response: Response<ProvinceCityModel>) {
                    if (response.isSuccessful){
                        val results = response.body()
                        results?.let {
                            setArrayOfCity(it.results)
                        }
                    }
                }

                override fun onFailure(call: Call<ProvinceCityModel>, t: Throwable) {
                    Log.d("Faskes Fragment Error : ",t.toString())
                }
            })
    }
    private fun getFaksesFromAPI(){
//        get String from Province & City AutoCompleteTextView
        val selectedProvince =  getSelectedProvince()
        val selectedCity = getSelectedCity()
        ApiService.endpoint.getFaskes(selectedProvince,selectedCity)
            .enqueue(object : Callback<FaskesModel> {
                override fun onResponse(call: Call<FaskesModel>, response: Response<FaskesModel>) {
                    if (response.isSuccessful){
                        val results = response.body()
                        results?.let {
                            showData(it)
                        }
                    }
                }

                override fun onFailure(call: Call<FaskesModel>, t: Throwable) {
                    Log.d("Faskes Fragment Error : ",t.toString())
                }
            })
    }

    //[Getter] untuk mendapatkan provinsi dan kota
    private fun getSelectedProvince() : String {
        val province_auto_complete_text_view = view?.findViewById(R.id.ProvinceDropdownMenu) as AutoCompleteTextView
        return province_auto_complete_text_view.text.toString()
    }
    private fun getSelectedCity() : String {
        val province_auto_complete_text_view = view?.findViewById(R.id.CityDropdownMenu) as AutoCompleteTextView
        return province_auto_complete_text_view.text.toString()
    }

    // function for make province and city dropdown list
    private fun setArrayOfProvince(PlaceList : List<Place>) {
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
    private fun setArrayOfCity(PlaceList : List<Place>) {
        val dropdownArray = ArrayList<String>()
        for (item in PlaceList) {
            dropdownArray.add(item.value)
        }
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_list_menu,
            dropdownArray
        )
        binding.CityDropdownMenu?.setAdapter(adapter)
    }

    private fun showData(items: FaskesModel){
        if (items.success){
            val faskes = items.data
            faskesAdapter.setData(faskes)
        }

    }

    // biar ga memory leak
    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}
