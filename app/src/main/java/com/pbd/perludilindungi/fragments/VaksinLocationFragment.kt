package com.pbd.perludilindungi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pbd.perludilindungi.*
import com.pbd.perludilindungi.services.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class VaksinLocationFragment : Fragment() {

    //atribut
    lateinit var faskesAdapter: FaskesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaksin_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val provinceAutoCompleteTextView = view.findViewById(R.id.ProvinceDropdownMenu) as AutoCompleteTextView
        provinceAutoCompleteTextView.setOnClickListener {
            getProvinceFromAPI()
        }
        provinceAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            getCityFromAPI()
        }
        val cityAutoCompleteTextView = view.findViewById(R.id.CityDropdownMenu) as AutoCompleteTextView
        cityAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->

            setupRecyclerView()
            getFaskesFromAPI()
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
                mFragmentManager.beginTransaction().apply {
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

                    Log.d("GET Province Error : ",t.toString())
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
                    Log.d("GET City Error : ",t.toString())
                }
            })
    }
    private fun getFaskesFromAPI(){
//        get String from Province & City AutoCompleteTextView
        val selectedProvince =  getSelectedProvince()
        val selectedCity = getSelectedCity()
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_barFaskes)
        progressBar.visibility = View.VISIBLE
        ApiService.endpoint.getFaskes(selectedProvince,selectedCity)
            .enqueue(object : Callback<FaskesModel> {
                override fun onResponse(call: Call<FaskesModel>, response: Response<FaskesModel>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful){
                        val results = response.body()
                        results?.let {
                            showData(it)
                        }
                    }
                }

                override fun onFailure(call: Call<FaskesModel>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.d("GET Faskes Error : ",t.toString())
                }
            })
    }

    //[Getter] untuk mendapatkan provinsi dan kota
    private fun getSelectedProvince() : String {
        val provinceAutoCompleteTextView = view?.findViewById(R.id.ProvinceDropdownMenu) as AutoCompleteTextView

        return provinceAutoCompleteTextView.text.toString()
    }
    private fun getSelectedCity() : String {
        val cityAutoCompleteTextView = view?.findViewById(R.id.CityDropdownMenu) as AutoCompleteTextView
        return cityAutoCompleteTextView.text.toString()
    }

    // function for make province and city dropdown list
    private fun setArrayOfProvince(PlaceList : List<Place>) {
        val dropdownArray = ArrayList<String>()
        for (item in PlaceList) {
            dropdownArray.add(item.value)
        }

        val adapter = getActivity()?.let {
            ArrayAdapter(
                it.getBaseContext(),
                R.layout.dropdown_list_menu,
                dropdownArray
            )
        }
        val provinceTextView = view?.findViewById(R.id.ProvinceDropdownMenu) as AutoCompleteTextView
        provinceTextView.setAdapter(adapter)
    }
    private fun setArrayOfCity(PlaceList : List<Place>) {
        val dropdownArray = ArrayList<String>()
        for (item in PlaceList) {
            dropdownArray.add(item.value)
        }
        val adapter = getActivity()?.let {
            ArrayAdapter(
                it.getBaseContext(),
                R.layout.dropdown_list_menu,
                dropdownArray
            )
        }
        val cityTextView = view?.findViewById(R.id.CityDropdownMenu) as AutoCompleteTextView
        cityTextView.setAdapter(adapter)

    }

    private fun showData(items: FaskesModel){
        if (items.success){
            val faskes = items.data
            faskesAdapter.setData(faskes)
        }

    }

}
