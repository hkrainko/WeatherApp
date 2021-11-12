package com.rk.weatherapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.rk.weatherapp.R
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.ui.city.CityFragment
import com.rk.weatherapp.ui.city.CityViewModel
//import com.rk.weatherapp.ui.local.LocalCityViewModelFactory
import com.rk.weatherapp.ui.search.SearchFragment
import com.rk.weatherapp.ui.search.SearchViewModel
import com.rk.weatherapp.ui.search.history.SearchHistoryFragment
import com.rk.weatherapp.ui.search.history.SearchHistoryViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var searchView: SearchView

    private lateinit var locationProviderClient: FusedLocationProviderClient

    private val searchFragment by lazy {
        SearchFragment.newInstance(object : SearchFragment.OnCityItemClickListener {
            override fun onCityItemClick(city: City) {
                viewModel.onClickSearchResult(city)
                searchView.clearFocus()
                searchView.onActionViewCollapsed()
            }
        })
    }

    private lateinit var searchHistoryFragment: SearchHistoryFragment
    private lateinit var searchHistoryFragmentContainerView: FragmentContainerView

    private val cityFragment: CityFragment by lazy {
        CityFragment.newInstance(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {
                Log.d("MainActivity", "onQueryTextChange:${qString}")
                viewModel.onQueryTextChange(qString)
                return true
            }

            override fun onQueryTextSubmit(qString: String): Boolean {
                Log.d("MainActivity", "onQueryTextSubmit:${qString}")
                return true
            }
        })

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            setFragmentContainer(hasFocus)
        }

        searchHistoryFragmentContainerView = view.findViewById(R.id.searchHistoryFragmentContainerView)
//        setFragmentContainer(false)
//        childFragmentManager.beginTransaction()
//            .replace(R.id.searchHistoryFragmentContainerView, searchHistoryFragment).commit()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        searchHistoryFragment = SearchHistoryFragment.newInstance(object :
            SearchHistoryFragment.OnSearchHistoryItemClickListener {
            override fun onSearchHistoryItemClick(city: City) {
                viewModel.onClickSearchHistory(city)
            }

            override fun onSearchHistoryDeleteClick(city: City) {
                viewModel.onClickSearchHistoryDelete(city.id)
            }
        })

        // let viewModel own the child viewModel
        viewModel.searchHistoryVm = ViewModelProvider(this).get(SearchHistoryViewModel::class.java)
        viewModel.cityViewModel = ViewModelProvider(
            this,
//            LocalCityViewModelFactory(null)
        ).get(CityViewModel::class.java)
        viewModel.searchVm = ViewModelProvider(this).get(SearchViewModel::class.java)

        searchHistoryFragment.viewModel = viewModel.searchHistoryVm
        cityFragment.viewModel = viewModel.cityViewModel
        searchFragment.viewModel = viewModel.searchVm

        childFragmentManager.beginTransaction()
            .replace(R.id.searchHistoryFragmentContainerView, searchHistoryFragment).commit()

        setFragmentContainer(false)
    }

    override fun onResume() {
        super.onResume()
        loadLastLocation()
        viewModel.onResume()
    }

    private fun setFragmentContainer(isSearching: Boolean) {
        if (isSearching) {
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, searchFragment).commit()
            searchHistoryFragmentContainerView.visibility = View.GONE
        } else {
            childFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, cityFragment).commit()
            searchHistoryFragmentContainerView.visibility = View.VISIBLE
        }
    }

    // location service
    // TODO: move to a service
    private fun loadLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                locationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        Log.d("Debug:", "Location:" + location.longitude)
                        viewModel.onLocationUpdate(location.latitude, location.longitude)
                    }
                }
            } else {
                Toast.makeText(context, "Please Turn on Your device Location", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        val ctx = activity ?: return false
        if (
            ActivityCompat.checkSelfPermission(
                ctx,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                ctx,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermission() {
        val ctx = activity ?: return
        ActivityCompat.requestPermissions(
            ctx,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            1000
        )
    }

    private fun isLocationEnabled(): Boolean {
        val ctx = activity ?: return false
        var locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    fun newLocationData() {
        var locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority = PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        // TODO: no permission handling
        locationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "last location: " + lastLocation.longitude.toString())
            viewModel.onLocationUpdate(lastLocation.latitude, lastLocation.longitude)
        }
    }

}