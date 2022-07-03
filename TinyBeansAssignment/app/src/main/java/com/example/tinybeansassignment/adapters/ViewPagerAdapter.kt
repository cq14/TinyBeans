package com.example.tinybeansassignment.adapters

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tinybeansassignment.R
import com.example.tinybeansassignment.models.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


class ViewPagerAdapter(val context: Context, val images: List<Image>): PagerAdapter() {
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        return super.instantiateItem(container, position)

        val layoutInflater = LayoutInflater.from(context)//context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.viewpager_layout, null)
        //(container as ViewPager).addView(view)
        val imageView: ImageView = view.findViewById(R.id.view_pager_imageview) as ImageView
        Glide.with(context)
            .load(images[position].url)
            .apply(RequestOptions().fitCenter().transform(RoundedCorners(8)))
            .into(imageView)
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        container.removeView(`object` as LinearLayout)
    }
    lateinit var Update: Runnable
    var freezeUpdate = false
    fun setTimer(myPager: ViewPager, numPages: Int, curPage: Int) {
        Update = object : Runnable {
            var NUM_PAGES = numPages
            var currentPage = curPage
            override fun run() {
                if(!freezeUpdate){
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0
                    }
                    myPager.setCurrentItem(currentPage++, true)
                }
            }
        }
        loop()
    }

    private fun loop(){
        CoroutineScope(IO).launch {
            delay(3000)
            CoroutineScope(Main).launch {
                Update.run()
                loop()
            }
        }
    }
}