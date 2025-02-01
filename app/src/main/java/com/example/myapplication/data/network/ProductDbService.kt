package com.example.myapplication.data.network
import com.example.myapplication.data.model.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path


interface ProductDbService {
    @GET("api/products")
    suspend  fun getProducts():List<Product>

    @GET("api/products/{id}")
    suspend  fun getProduct(@Path("id") id:Int): Product


    @POST("api/products")
    suspend fun createProduct(@Body product: Product): Product

    @PUT("api/products/{id}")
    suspend fun updateProduct(@Path("id") id:Int,@Body product: Product): Product

    @DELETE("api/products/{id}")
    suspend fun deleteProduct(@Path("id") id:Int):Boolean




}