package com.rk.weatherapp.ui.search.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rk.weatherapp.R
import com.rk.weatherapp.databinding.AdapterSearchHistoryBinding
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.infrastructure.network.GlideImageLoader
import com.rk.weatherapp.infrastructure.toOpenWeatherUrl

class SearchHistoryFragment(private val listener: OnSearchHistoryItemClickListener) : Fragment() {

    companion object {
        fun newInstance(listener: OnSearchHistoryItemClickListener) = SearchHistoryFragment(listener)
    }

    lateinit var viewModel: SearchHistoryViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.searchHistoryRecyclerView)
        recyclerView.apply {
            adapter = SearchHistoryAdapter(object : OnSearchHistoryItemClickListener {
                override fun onSearchHistoryItemClick(city: City) {
                    listener.onSearchHistoryItemClick(city)
                }
            })
            layoutManager = LinearLayoutManager(context)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchHistoryViewModel::class.java)
        viewModel.cities.observe(viewLifecycleOwner, Observer { cities ->
            (recyclerView.adapter as SearchHistoryFragment.SearchHistoryAdapter).setCitiesList(cities)
        })
    }

    // inner classes
    private inner class ViewHolder constructor(binding: AdapterSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val cityTV: TextView = binding.cityTV
        val tempTV: TextView = binding.tempTV
        val weatherCondIV: ImageView = binding.weatherCondIV

        fun bind(city: City, clickListener: OnSearchHistoryItemClickListener) {
            cityTV.text = city.name
            tempTV.text =
                if (city.weather?.temp != null) city.weather!!.temp.toString() else "-"
            if (context != null) {
                GlideImageLoader.loadImage(
                    context!!, city.weather?.condition?.type?.toOpenWeatherUrl(), weatherCondIV)
            }
            itemView.setOnClickListener {
                clickListener.onSearchHistoryItemClick(city)
            }
        }
    }

    private inner class SearchHistoryAdapter internal constructor(
        var clickListener: OnSearchHistoryItemClickListener,
        private var cities: MutableList<City> = mutableListOf<City>()
    ) :
        RecyclerView.Adapter<SearchHistoryFragment.ViewHolder>() {

        fun setCitiesList(cities: List<City>) {
            this.cities = cities.toMutableList()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SearchHistoryFragment.ViewHolder {

            return ViewHolder(
                AdapterSearchHistoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }

        override fun onBindViewHolder(holder: SearchHistoryFragment.ViewHolder, position: Int) {
            val city = cities[position]
//            holder.cityTV.text = city.name
//            holder.tempTV.text =
//                if (city.weather?.temp != null) city.weather!!.temp.toString() else "-"
            holder.bind(city, clickListener)
        }

        override fun getItemCount(): Int {
            return cities.size
        }
    }

    interface OnSearchHistoryItemClickListener {
        fun onSearchHistoryItemClick(city: City)
    }
}