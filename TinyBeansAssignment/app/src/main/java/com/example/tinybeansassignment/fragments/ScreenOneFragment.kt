package com.example.tinybeansassignment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinybeansassignment.MainActivity
import com.example.tinybeansassignment.R
import com.example.tinybeansassignment.SimpleDividerItem
import com.example.tinybeansassignment.databinding.FragmentScreenOneBinding
import com.example.tinybeansassignment.service.ApiService
import com.example.tinybeansassignment.service.ContentService
import com.example.tinybeansassignment.viewmodels.ScreenOneViewModel

// TODO: Rename parameter arguments, choose names that match

class ScreenOneFragment : Fragment() {
    private lateinit var viewModel: ScreenOneViewModel
    private lateinit var contentRecycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ScreenOneViewModel(ContentService(ApiService(requireContext())))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentScreenOneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_screen_one, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if(it)
                (activity as MainActivity).showLoading()
            else{
                (activity as MainActivity).showLoading(false)
                viewModel.contentAdapter.notifyDataSetChanged()
            }
        })
        viewModel.screenTwoContent.observe(viewLifecycleOwner, Observer {
            if(it != null && it.isNotEmpty()){
                //successful response
                (activity as MainActivity).changeFragment(ScreenTwoFragment(it))
            }
            else{
                (activity as MainActivity).displayAlert("Error", "There was an issue loading images for Screen Two.")
            }
        })
        val view = binding.root
        contentRecycler = view.findViewById(R.id.content_one_recycler)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        contentRecycler.layoutManager = LinearLayoutManager(requireContext())
        contentRecycler.adapter = viewModel.contentAdapter
        //contentRecycler.addItemDecoration(SimpleDividerItem(requireContext()))
    }

}