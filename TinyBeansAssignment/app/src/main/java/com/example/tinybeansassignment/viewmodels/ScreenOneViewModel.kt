package com.example.tinybeansassignment.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinybeansassignment.adapters.ContentListAdapter
import com.example.tinybeansassignment.models.Content
import com.example.tinybeansassignment.models.ContentResponse
import com.example.tinybeansassignment.models.Image
import com.example.tinybeansassignment.service.ContentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScreenOneViewModel(val contentService: ContentService): ViewModel() {
    var contentList = listOf<Content>()
    var screenTwoContent = MutableLiveData<List<Image>>()
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val contentAdapter: ContentListAdapter = ContentListAdapter(this, contentService.apiService.context)

    fun loadData(){
        isLoading.value = true
        GlobalScope.launch(Dispatchers.IO) {
            try{
                val response = contentService.getContent().await()
                if(response?.content != null){
                    //need to reformat to handle url to image
                    for(content in response.content){
                        //add images to carousel object
                        if(content.url != null && content.url.contains("mockable.io")){
                            var path = content.url.split("mockable.io")[1]
                            val innerResponse = contentService.getImagesFromContent(path).await()
                            if(innerResponse?.images != null){
                                content.images = innerResponse.images
                            }
                        }
                    }
                    contentList = response.content
                }
            }catch(exc: Exception){
                Log.e("ScreenOneVM.loadData():", "$exc")
            }
            isLoading.postValue(false)
        }
    }

    fun loadScreenTwoData(){
        isLoading.value = true
        GlobalScope.launch(Dispatchers.IO){
            try{
                val response = contentService.getListContent().await()
                screenTwoContent.postValue(response?.images)
            }catch(exc: Exception){
                Log.e("loadScreenTwoData():", "$exc")
            }
            isLoading.postValue(false)
        }
    }

    fun getContentAt(position: Int): Content{
        return contentList[position]
    }

    fun isImageType(position: Int): Boolean{
        return getContentAt(position).type == "image"
    }

    fun isSliderType(position: Int): Boolean{
        return getContentAt(position).type == "slider"
    }

    fun isCarouselType(position: Int): Boolean{
        return getContentAt(position).type == "carousel"
    }

    fun getImages(position: Int): List<Image>{
        return getContentAt(position).images
    }
}