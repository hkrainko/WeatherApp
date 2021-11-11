package com.rk.weatherapp.ui.search.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rk.weatherapp.R

class SearchResultFragment : Fragment() {

    companion object {
        fun newInstance() = SearchResultFragment()
    }

    private lateinit var viewModel: SearchResultViewModel

    private lateinit var backButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        backButton = view.findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener { view.findNavController().popBackStack() }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchResultViewModel::class.java)
        // TODO: Use the ViewModel
    }

}