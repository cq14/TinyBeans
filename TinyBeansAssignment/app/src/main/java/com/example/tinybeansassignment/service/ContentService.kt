package com.example.tinybeansassignment.service

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ContentService(val apiService: ApiService) {
    private val tag = this.javaClass.simpleName

    fun getContent() = CoroutineScope(Dispatchers.IO).async {
        try{
            val response = apiService.createTinyBeanInterface().getContent().execute()
            return@async response.body()
        }catch (exc: Exception){
            Log.e(tag, "Exception caught on ContentService.getContent(): $exc")
            return@async null
        }
    }

    fun  getImagesFromContent(path: String) = CoroutineScope(Dispatchers.IO).async {
        try{
            val response = apiService.createTinyBeanInterface().getImagesFromContent(path).execute()
            return@async response.body()
        }catch(exc: Exception){
            Log.e(tag, "Exception caught on ContentService.getImagesFromContent(): $exc")
            return@async null
        }
    }

}