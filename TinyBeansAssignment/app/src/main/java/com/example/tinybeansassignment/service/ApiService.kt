package com.example.tinybeansassignment.service

import android.content.Context
import com.example.tinybeansassignment.R
import com.example.tinybeansassignment.models.ContentResponse
import com.example.tinybeansassignment.models.ImagesFromContentResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Url

class ApiService(val context: Context) {
    private fun createRetrofitInstance(baseUrl: String): Retrofit{
        val clientBuilder = OkHttpClient().newBuilder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = clientBuilder
            .addNetworkInterceptor(interceptor)
            .build()
        val gson = GsonBuilder().serializeNulls().create()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun createTinyBeanInterface(): TinyBeansInterface{
        return createRetrofitInstance(context.getString(R.string.base_url)).create()
    }

}

interface TinyBeansInterface{
    @GET("/content")
    fun getContent(): Call<ContentResponse>
    @GET()
    fun getImagesFromContent(@Url url: String): Call<ImagesFromContentResponse>
}