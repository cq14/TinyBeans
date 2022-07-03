package com.example.tinybeansassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tinybeansassignment.viewmodels.ScreenOneViewModel
import com.example.tinybeansassignment.BR
import com.example.tinybeansassignment.R
import com.example.tinybeansassignment.models.Image

class CarouselAdapter(val images: List<Image>, val context: Context): RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_carousel, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(images[position].url)
            .apply(RequestOptions().fitCenter().transform(RoundedCorners(8)))
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.carouselImage)
    }


}