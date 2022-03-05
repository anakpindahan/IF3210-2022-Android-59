package com.pbd.perludilindungi.fragments



import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pbd.perludilindungi.*
import com.pbd.perludilindungi.services.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VaksinLocationFragment : Fragment(){

    //atribut
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var faskesAdapter: FaskesAdapter

    companion object{
        var latitude = String()
        var longitude = String()
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)
        checkLocationPermission()
        val provinceAutoCompleteTextView =
            view.findViewById(R.id.ProvinceDropdownMenu) as AutoCompleteTextView
        provinceAutoCompleteTextView.setOnClickListener {
            getProvinceFromAPI()
        }
        provinceAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            getCityFromAPI()
        }
        val cityAutoCompleteTextView =
            view.findViewById(R.id.CityDropdownMenu) as AutoCompleteTextView
        cityAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            getCurrentLocation()
            setupRecyclerView()
            getFaskesFromAPI()
        }
    }


    private fun checkLocationPermission(){

        //cek permission granted or not
        //if not granted request permission
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)

        }
        else{
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(){
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if(it != null){
                latitude = it.latitude.toString()
                longitude = it.longitude.toString()
            }
        }

    }

    //function for setup recyclerview for faskes
    private fun setupRecyclerView() {
        faskesAdapter = FaskesAdapter(arrayListOf(), object : FaskesAdapter.OnAdapterListener {
            override fun onClick(result: Data) {
                val detailLocationFragment = VaksinLocationDetailFragment()
                val mbundle = Bundle()
                mbundle.putParcelable("EXTRA_FASKES", result)
                detailLocationFragment.arguments = mbundle
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragment_container,
                        detailLocationFragment,
                        VaksinLocationDetailFragment::class.java.simpleName
                    )
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

    private fun getProvinceFromAPI() {
        ApiService.endpoint.getProvince()
            .enqueue(object : Callback<ProvinceCityModel> {
                override fun onResponse(
                    call: Call<ProvinceCityModel>,
                    response: Response<ProvinceCityModel>
                ) {
                    if (response.isSuccessful) {
                        val results = response.body()
                        results?.let {
                            setArrayOfProvince(it.results)
                        }
                    }
                }

                override fun onFailure(call: Call<ProvinceCityModel>, t: Throwable) {
                    Log.d("GET Province Error : ", t.toString())
                }
            })
    }

    private fun getCityFromAPI() {
//        get String from Province AutoCompleteTextView
        val selectedItem = getSelectedProvince()
        ApiService.endpoint.getCity(selectedItem)
            .enqueue(object : Callback<ProvinceCityModel> {
                override fun onResponse(
                    call: Call<ProvinceCityModel>,
                    response: Response<ProvinceCityModel>
                ) {
                    if (response.isSuccessful) {
                        val results = response.body()
                        results?.let {
                            setArrayOfCity(it.results)
                        }
                    }
                }

                override fun onFailure(call: Call<ProvinceCityModel>, t: Throwable) {
                    Log.d("GET City Error : ", t.toString())
                }
            })
    }

    private fun getFaskesFromAPI() {
//        get String from Province & City AutoCompleteTextView
        val selectedProvince = getSelectedProvince()
        val selectedCity = getSelectedCity()
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_barFaskes)
        progressBar.visibility = View.VISIBLE
        ApiService.endpoint.getFaskes(selectedProvince, selectedCity)
            .enqueue(object : Callback<FaskesModel> {
                override fun onResponse(call: Call<FaskesModel>, response: Response<FaskesModel>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val results = response.body()
                        results?.let {
                            showData(it)
                        }
                    }
                }

                override fun onFailure(call: Call<FaskesModel>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.d("GET Faskes Error : ", t.toString())
                }
            })
    }

    //[Getter] untuk mendapatkan provinsi dan kota
    private fun getSelectedProvince(): String {
        val provinceAutoCompleteTextView =
            view?.findViewById(R.id.ProvinceDropdownMenu) as AutoCompleteTextView

        return provinceAutoCompleteTextView.text.toString()
    }

    private fun getSelectedCity(): String {
        val cityAutoCompleteTextView =
            view?.findViewById(R.id.CityDropdownMenu) as AutoCompleteTextView
        return cityAutoCompleteTextView.text.toString()
    }

    // function for make province and city dropdown list
    private fun setArrayOfProvince(PlaceList: List<Place>) {
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

    private fun setArrayOfCity(PlaceList: List<Place>) {
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

    private fun showData(items: FaskesModel) {
        if (items.success) {
            val faskes = items.data

            val nearestFaskestList = getNearestFaskes(latitude , longitude,faskes)
            faskesAdapter.setData(nearestFaskestList)
//            faskesAdapter.setData(faskes)
        }
    }

    private fun getNearestFaskes(latitude : String,longitude: String, faskesList : List<Data>) : List<Data> {
        val data : MutableList<Data> = mutableListOf()
        val givenMap = hashMapOf<Int, Double>()
        var ukuran = 5
        if(faskesList.size <5) ukuran = faskesList.size
        for(faskes in faskesList){
            val faskesLatitude = faskes.latitude
            val faskesLongitude = faskes.longitude
            val jarak = distanceInKm(faskesLatitude!!.toDouble(),
                        faskesLongitude!!.toDouble(),
                        latitude.toDouble(),
                        longitude.toDouble()
            )
            givenMap[faskes.id] = jarak
        }
        val sortedMap = givenMap.toList().sortedBy { (k, v) -> v }.toMap()
        for(sortedFaskes in sortedMap){
            for(faskes in faskesList){
                if(faskes.id == sortedFaskes.key && data.size != ukuran){
                    data.add(faskes)
                }
                if(data.size == ukuran)break
            }
            if(data.size == ukuran)break
        }
        return data.toList()
    }

    fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        dist = dist * 1.609344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}
