package com.rk.weatherapp.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rk.weatherapp.R
import com.rk.weatherapp.databinding.LocalCityFragmentBinding
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.infrastructure.network.GlideImageLoader
import com.rk.weatherapp.infrastructure.toDisplayHHmm
import com.rk.weatherapp.infrastructure.toOpenWeatherUrl

class CityFragment(private val weather: Weather?) : Fragment() {

    companion object {
        fun newInstance(weather: Weather?) = CityFragment(weather)
    }

    lateinit var viewModel: CityViewModel

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
        binding.backImageButton.setOnClickListener {
            viewModel.onClickBackButton()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(
//            this,
//            LocalCityViewModelFactory(weather)
//        ).get(LocalCityViewModel::class.java)
        viewModel.displayCity.observe(viewLifecycleOwner, {
            binding.cityNameTv.text = it?.weather?.cityName ?: "-"
            binding.conditionIv
            GlideImageLoader.loadImage(
                binding.conditionIv.context,
                it?.weather?.condition?.type?.toOpenWeatherUrl(),
                binding.conditionIv
            )
            binding.conditionDescTv.text = it?.weather?.condition?.desc ?: "-"
            binding.tempTv.text = "${it?.weather?.temp ?: "-"}º"
            binding.highTempTv.text = "H ${it?.weather?.tempMax ?: "-"}º"
            binding.lowTempTv.text = "L ${it?.weather?.tempMin ?: "-"}º"
            binding.pressureTV.text = if(it?.weather?.pressure != null) it?.weather?.pressure.toString() else "-"
            binding.humidityTV.text = "${it?.weather?.humidity ?: "-"}%"
            binding.sunriseTV.text = it?.weather?.sunrise?.toDisplayHHmm() ?: "-"
            binding.sunsetTV.text = it?.weather?.sunset?.toDisplayHHmm() ?: "-"
        })
        viewModel.isLocal.observe(viewLifecycleOwner, {
            binding.backImageButton.visibility = if (it) View.GONE else View.VISIBLE
            binding.localImageView.visibility =  if (it) View.VISIBLE else View.GONE
        })
    }

}