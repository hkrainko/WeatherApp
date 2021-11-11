package com.rk.weatherapp.ui.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rk.weatherapp.R
import com.rk.weatherapp.databinding.LocalCityFragmentBinding
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.infrastructure.network.GlideImageLoader
import com.rk.weatherapp.infrastructure.toDisplayHHmm
import com.rk.weatherapp.infrastructure.toOpenWeatherUrl

class LocalCityFragment(private val weather: Weather?) : Fragment() {

    companion object {
        fun newInstance(weather: Weather?) = LocalCityFragment(weather)
    }

    lateinit var viewModel: LocalCityViewModel

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
//        viewModel = ViewModelProvider(
//            this,
//            LocalCityViewModelFactory(weather)
//        ).get(LocalCityViewModel::class.java)
        viewModel.city.observe(viewLifecycleOwner, Observer { city ->
            binding.cityNameTv.text = city?.weather?.cityName ?: "-"
            binding.conditionIv
            GlideImageLoader.loadImage(
                binding.conditionIv.context,
                city?.weather?.condition?.type?.toOpenWeatherUrl(),
                binding.conditionIv
            )
            binding.conditionDescTv.text = city?.weather?.condition?.desc ?: "-"
            binding.tempTv.text = "${city?.weather?.temp ?: "-"}º"
            binding.highTempTv.text = "H ${city?.weather?.tempMax ?: "-"}º"
            binding.lowTempTv.text = "L ${city?.weather?.tempMin ?: "-"}º"
            binding.pressureTV.text = if(city?.weather?.pressure != null) city?.weather?.pressure.toString() else "-"
            binding.humidityTV.text = "${city?.weather?.humidity ?: "-"}%"
            binding.sunriseTV.text = city?.weather?.sunrise?.toDisplayHHmm() ?: "-"
            binding.sunsetTV.text = city?.weather?.sunset?.toDisplayHHmm() ?: "-"
        })
    }

}