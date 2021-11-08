package com.rk.weatherapp.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rk.weatherapp.R
import com.rk.weatherapp.databinding.AdapterCityBinding
import com.rk.weatherapp.databinding.FragmentSearchHistoryItemBinding
import com.rk.weatherapp.domain.entities.City

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    // Inner class
    private inner class ViewHolder internal constructor(binding: AdapterCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal val textView: TextView = binding.textView
    }

    private inner class SearchCityAdapter internal constructor(private var cities: MutableList<City> = mutableListOf<City>()) :
        RecyclerView.Adapter<SearchFragment.ViewHolder>() {

        fun setMovieList(cities: List<City>) {
            this.cities = cities.toMutableList()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFragment.ViewHolder {

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
        }

        override fun getItemCount(): Int {
            return cities.size
        }
    }

}