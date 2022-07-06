package com.example.tinybeansassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.tinybeansassignment.BR
import com.example.tinybeansassignment.R
import com.example.tinybeansassignment.viewmodels.ScreenTwoViewModel
import com.squareup.picasso.Picasso


class GridAdapter(val viewModel: ScreenTwoViewModel): RecyclerView.Adapter<GridAdapter.ImageGridView>() {

    private val FADE_DURATION: Long = 1000

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGridView {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_grid_image, parent, false)
        return ImageGridView(binding)
    }

    override fun onBindViewHolder(holder: ImageGridView, position: Int) {
        holder.bind(viewModel, position)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.carouselImage)
        val image = viewModel.listImages[position]
        Picasso.get().load(image.url)
            .resize(image.width, image.height)
            .into(imageView)
        setFadeAnimation(holder.itemView);
    }

    override fun getItemCount(): Int {
        return viewModel.listImages.size
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION
        view.startAnimation(anim)
    }

    class ImageGridView(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(viewModel: ScreenTwoViewModel, position: Int){
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }
    }
}