package com.example.tinybeansassignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tinybeansassignment.BR
import com.example.tinybeansassignment.R
import com.example.tinybeansassignment.viewmodels.ScreenOneViewModel
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import com.squareup.picasso.Picasso


class ContentListAdapter(val viewModel: ScreenOneViewModel, val context: Context): RecyclerView.Adapter<ContentListAdapter.ContentView>() {
    var mapImageViewArray = mutableMapOf<Int,ImageView>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentView {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_content_view, parent, false)
        return ContentView(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ContentView, position: Int) {
        holder.bind(viewModel, position)

        //initiate map - different ImageView per row
        mapImageViewArray[0] = holder.itemView.findViewById(R.id.image1)
        mapImageViewArray[1] = holder.itemView.findViewById(R.id.image2)
        mapImageViewArray[2] = holder.itemView.findViewById(R.id.image3)
        mapImageViewArray[3] = holder.itemView.findViewById(R.id.image4)

        //reset status
        for(i in 0..4){
            mapImageViewArray[i]?.visibility = View.GONE
        }
        val viewPager = holder.itemView.findViewById<ViewPager>(R.id.sliderImage)
        val carouselView = holder.itemView.findViewById<CarouselRecyclerview>(R.id.carouselRecyclerView)
        carouselView.visibility = if(viewModel.isCarouselType(position)) View.VISIBLE else View.GONE
        viewPager.visibility = if(viewModel.isSliderType(position)) View.VISIBLE else View.GONE
        val images = viewModel.getImages(position)
        if(viewModel.isImageType(position)){
            //todo error handling
            for(i in images.indices){
                if(i < 4){      // fail-safe
                    mapImageViewArray[i]?.visibility = View.VISIBLE
                    if(images[i].url.contains(".gif")){
                        if(mapImageViewArray[i] != null){
                            //need context
                            //todo add placeholder
                            Glide.with(holder.itemView.context)
                                .asGif()
                                .load(images[i].url)
                                .placeholder(R.drawable.ic_baseline_gif_24)
                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                                .into(mapImageViewArray[i]!!)
                        }
                    }
                    else{
                        Picasso.get().load(images[i].url)
                            .resize(images[i].width, images[i].height)
                            .into(mapImageViewArray[i])
                    }
                }
            }
        }
        if(viewModel.isSliderType(position)){
            val adapter = ViewPagerAdapter(context, images)
            adapter.setTimer(viewPager,viewModel.getContentAt(position).images.size,0)
            viewPager.adapter = adapter
            //disable automatic slide while user is performing slide
            viewPager.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> adapter.freezeUpdate = true
                    MotionEvent.ACTION_UP -> adapter.freezeUpdate = false
                }
                v?.onTouchEvent(event) ?: true
            }
        }
        if(viewModel.isCarouselType(position)){
            carouselView.adapter = CarouselAdapter(viewModel.getImages(position), holder.itemView.context)
            carouselView.set3DItem(true)
            carouselView.setInfinite(true)
            carouselView.setAlpha(true)
            carouselView.setIsScrollingEnabled(true)
            val carouselLayoutManager = carouselView.getCarouselLayoutManager()
            val currentlyCenterPosition = carouselView.getSelectedPosition()
        }




    }

    override fun getItemCount(): Int {
        return viewModel.contentList.size
    }

    class ContentView(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(viewModel: ScreenOneViewModel, position: Int){
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }
    }
}