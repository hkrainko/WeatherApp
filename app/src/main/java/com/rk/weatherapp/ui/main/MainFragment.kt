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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.rk.weatherapp.R
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.ui.local.LocalCityFragment
import com.rk.weatherapp.ui.search.SearchFragment
import com.rk.weatherapp.ui.search.history.SearchHistoryFragment

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
                viewModel.onClickSearchResult(city.id)
                searchView.clearFocus()
                searchView.onActionViewCollapsed()
                val action =
                    MainFragmentDirections
                        .actionMainFragmentToSearchResultFragment()
                view?.findNavController()?.navigate(action)
            }
        })
    }

    private val searchHistoryFragment by lazy {
        SearchHistoryFragment.newInstance()
    }

    private val localCityFragment: LocalCityFragment by lazy {
        LocalCityFragment.newInstance(null)
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
        setFragmentContainer(false)
//        setFragmentContainer(searchView.isFocused)

//        val dialogFragment: SearchHistoryDialogFragment =
//            SearchHistoryDialogFragment.newInstance(10)
//        dialogFragment.show(fragmentManager!!, "dialogFragment")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.cities.observe(viewLifecycleOwner, Observer { cities ->
            searchFragment.viewModel.cities.value = cities
        })

        viewModel.localCityWeather.observe(viewLifecycleOwner, {
            localCityFragment.viewModel.cityWeather.value = it
        })

        viewModel.historyCities.observe(viewLifecycleOwner, {
            searchHistoryFragment.viewModel.cities.value = it
        })
    }

    override fun onResume() {
        super.onResume()
        loadLastLocation()
        viewModel.onResume()
    }

    private fun setFragmentContainer(isSearching: Boolean) {
        if (isSearching) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, searchFragment).commit()
        } else {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, localCityFragment).commit()
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