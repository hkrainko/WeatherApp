package com.rk.weatherapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rk.weatherapp.R
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.ui.local.LocalCityFragment
import com.rk.weatherapp.ui.search.SearchFragment
import com.rk.weatherapp.ui.search.SearchHistoryDialogFragment
import com.rk.weatherapp.ui.search.SearchResultFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var searchView: SearchView

    private val searchFragment by lazy {
        SearchFragment.newInstance(object : SearchFragment.OnCityItemClickListener {
            override fun onCityItemClick(city: City) {
                searchView.clearFocus()
                searchView.onActionViewCollapsed()
                val action =
                    MainFragmentDirections
                        .actionMainFragmentToSearchResultFragment()
                view?.findNavController()?.navigate(action)
            }
        })
    }

    private val localCityFragment: LocalCityFragment by lazy {
        LocalCityFragment.newInstance(null)
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
    }

    override fun onResume() {
        super.onResume()
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

}