package com.rk.weatherapp.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rk.weatherapp.R
import com.rk.weatherapp.databinding.AdapterCityBinding
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.ui.main.MainFragmentDirections

class SearchFragment(private val listener: OnCityItemClickListener) : Fragment() {

    companion object {
        fun newInstance(listener: OnCityItemClickListener) = SearchFragment(listener)
    }

    lateinit var viewModel: SearchViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.search_result_recycler_view)
        recyclerView.apply {
            adapter = SearchCityAdapter(object : OnCityItemClickListener {
                override fun onCityItemClick(city: City) {
                    Log.d("SearchFragment", "onItemClick:${city.id}")
                    listener.onCityItemClick(city)
                }
            })
            layoutManager = LinearLayoutManager(context)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.cities.observe(viewLifecycleOwner, Observer { cities ->
            Log.d("SearchFragment", "cities:${cities.size}")
            (recyclerView.adapter as SearchCityAdapter).setCitiesList(cities)
        })
    }

    fun setCityItemClickListener(listener: OnCityItemClickListener) {

    }

    // Inner classes
    private inner class ViewHolder internal constructor(binding: AdapterCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal val textView: TextView = binding.textView

        fun bind(city: City, clickListener: OnCityItemClickListener) {
            textView.text = city.name

            itemView.setOnClickListener {
                clickListener.onCityItemClick(city)
            }
        }
    }

    private inner class SearchCityAdapter internal constructor(
        var cityItemClickListener: OnCityItemClickListener,
        private var cities: MutableList<City> = mutableListOf<City>()
    ) :
        RecyclerView.Adapter<SearchFragment.ViewHolder>() {

        fun setCitiesList(cities: List<City>) {
            this.cities = cities.toMutableList()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SearchFragment.ViewHolder {

            return ViewHolder(
                AdapterCityBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }

        override fun onBindViewHolder(holder: SearchFragment.ViewHolder, position: Int) {
            val city = cities[position]
            holder.textView.text = city.name
            holder.bind(city, cityItemClickListener)
        }

        override fun getItemCount(): Int {
            return cities.size
        }
    }

    interface OnCityItemClickListener {
        fun onCityItemClick(city: City)
    }

}