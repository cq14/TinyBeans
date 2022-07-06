package com.example.tinybeansassignment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinybeansassignment.R
import com.example.tinybeansassignment.databinding.FragmentScreenTwoBinding
import com.example.tinybeansassignment.models.Image
import com.example.tinybeansassignment.viewmodels.ScreenTwoViewModel


class ScreenTwoFragment(private val listImages: List<Image>) : Fragment() {

    private lateinit var gridRecycler: RecyclerView
    private lateinit var viewModel: ScreenTwoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ScreenTwoViewModel(listImages)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentScreenTwoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_screen_two, container, false )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        var view = binding.root
        gridRecycler = view.findViewById(R.id.gridRecyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        gridRecycler.adapter = viewModel.gridAdapter
    }

}