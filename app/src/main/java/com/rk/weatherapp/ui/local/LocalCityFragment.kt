package com.rk.weatherapp.ui.local

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.rk.weatherapp.R
import com.rk.weatherapp.databinding.LocalCityFragmentBinding

class LocalCityFragment : Fragment() {

    companion object {
        fun newInstance() = LocalCityFragment()
    }

    private lateinit var viewModel: LocalCityViewModel

    private lateinit var binding: LocalCityFragmentBinding

    private lateinit var cityNameTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LocalCityFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cityNameTv = view.findViewById(R.id.cityNameTv)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LocalCityViewModel::class.java)
        viewModel.cityWeather.observe(viewLifecycleOwner, Observer { cityWeather ->
            cityNameTv.text = cityWeather?.cityName ?: "-"
            binding.cityNameTv.text = cityWeather?.cityName ?: "-"
            binding.conditionDescTv.text = cityWeather?.condition?.desc ?: "-"
            binding.tempTv.text = "${cityWeather?.temp.toString()}º"
            binding.highTempTv.text = "H ${cityWeather?.tempMax.toString()}º"
            binding.lowTempTv.text = "L ${cityWeather?.tempMin.toString()}º"
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

}