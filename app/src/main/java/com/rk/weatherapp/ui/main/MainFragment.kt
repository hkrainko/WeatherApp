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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rk.weatherapp.R
import com.rk.weatherapp.ui.search.SearchHistoryDialogFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {
                Log.d("MainActivity", "onQueryTextChange:${qString}")
                if (qString.isNotEmpty()) {
                    viewModel.onQueryTextChange(qString)
                }
                return true
            }
            override fun onQueryTextSubmit(qString: String): Boolean {
                Log.d("MainActivity", "onQueryTextSubmit:${qString}")
                return true
            }
        })

//        val dialogFragment: SearchHistoryDialogFragment =
//            SearchHistoryDialogFragment.newInstance(10)
//        dialogFragment.show(fragmentManager!!, "dialogFragment")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.getCities().observe(this, Observer { cities ->
            print("cities:$cities")
        })
    }

}