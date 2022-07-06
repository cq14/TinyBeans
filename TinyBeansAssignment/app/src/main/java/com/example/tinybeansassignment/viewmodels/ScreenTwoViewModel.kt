package com.example.tinybeansassignment.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tinybeansassignment.adapters.GridAdapter
import com.example.tinybeansassignment.models.Image

//here for future use if we want to expand second viewmodel
class ScreenTwoViewModel(val listImages: List<Image>): ViewModel() {
    val gridAdapter: GridAdapter = GridAdapter(this)
}