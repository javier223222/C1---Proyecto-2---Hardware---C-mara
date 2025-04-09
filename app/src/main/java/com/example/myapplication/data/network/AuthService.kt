package com.example.myapplication.data.network
import com.example.myapplication.data.model.AuthResponse
import com.example.myapplication.data.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST
interface AuthService {
    @POST("api/auth/login")
    suspend fun login(@Body request:LoginRequest):AuthResponse
}