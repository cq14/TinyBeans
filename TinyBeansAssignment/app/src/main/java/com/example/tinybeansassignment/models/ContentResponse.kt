package com.example.tinybeansassignment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContentResponse(
    @SerializedName("content")
    val content: List<Content>
): Serializable

data class ImagesFromContentResponse(
    @SerializedName("images")
    val images: List<Image>
): Serializable

data class Content(
    //type, cols, images, show, height, title, url
    @SerializedName("type")
    val type: String,
    @SerializedName("show")
    val show: Int,
    @SerializedName("cols")
    val cols: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("images")
    var images: List<Image>
): Serializable

data class Image(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("format")
    val format: String
): Serializable

