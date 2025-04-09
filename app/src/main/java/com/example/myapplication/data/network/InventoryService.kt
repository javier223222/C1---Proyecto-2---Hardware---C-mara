package com.example.myapplication.data.network

import com.example.myapplication.data.model.InventoryItem
import com.example.myapplication.data.model.InventoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface InventoryService {
    @GET("api/inventory/mine")
    suspend fun getmineinventary(@Header("Authorization") authHeader:String):InventoryResponse
    @Multipart
    @POST("api/inventory/")
    suspend fun createInventary(
        @Header("Authorization") authHeader: String,
        @Part("productName") productName:RequestBody,
        @Part("description") description:RequestBody,
        @Part("quantity") quantity:RequestBody,
        @Part("location") location:RequestBody,
        @Part photo:MultipartBody.Part
    ):InventoryItem
    @Multipart
    @PUT("api/inventory/{id}")
    suspend fun updateInventoryProductName(
        @Path("id") id:Int,
        @Header("Authorization") authHeader:String,
        @Part("productName") productName:RequestBody,
        @Part("quantity") quantity:RequestBody,
        @Part("location") location:RequestBody,
        @Part("status") status:RequestBody,
        @Part photo:MultipartBody.Part
    ):InventoryItem
    @DELETE("/api/inventory/{id}")
    suspend fun deleteInventory(
        @Path("id") id:Int,
        @Header("Authorization") authHeader:String,
    )



}