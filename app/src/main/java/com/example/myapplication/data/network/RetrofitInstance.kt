package com.example.myapplication.data.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitInstance {
    private val retrofit=Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3001/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val authService:AuthService by lazy{

        retrofit.create(AuthService::class.java)
    }
    val inventoryService:InventoryService by lazy {
        retrofit.create(InventoryService::class.java)
    }

    val tokenService:RegisterTokenService by lazy{
        retrofit.create(RegisterTokenService::class.java)
    }

}