package com.rk.weatherapp.ui.local

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rk.weatherapp.R

class LocalCityFragment : Fragment() {

    companion object {
        fun newInstance() = LocalCityFragment()
    }

    private lateinit var viewModel: LocalCityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.local_city_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LocalCityViewModel::class.java)
        // TODO: Use the ViewModel
    }

}