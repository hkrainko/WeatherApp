package com.rk.weatherapp.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rk.weatherapp.R
import com.rk.weatherapp.databinding.AdapterCityBinding
import com.rk.weatherapp.databinding.FragmentSearchHistoryItemBinding
import com.rk.weatherapp.domain.entities.City

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
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
            adapter = SearchCityAdapter(object: OnItemClickListener {
                override fun onItemClick(city: City) {
                    Log.d("SearchFragment", "onItemClick:${city.id}")
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

    // Inner class
    private inner class ViewHolder internal constructor(binding: AdapterCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal val textView: TextView = binding.textView

        fun bind(city: City, clickListener: OnItemClickListener) {
            textView.text = city.name

            itemView.setOnClickListener {
                clickListener.onItemClick(city)
            }
        }
    }

    private inner class SearchCityAdapter internal constructor(
        var itemClickListener: OnItemClickListener,
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
            holder.bind(city, itemClickListener)
        }

        override fun getItemCount(): Int {
            return cities.size
        }
    }

    private interface OnItemClickListener {
        fun onItemClick(city: City)
    }

}