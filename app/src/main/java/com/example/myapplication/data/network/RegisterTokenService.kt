package com.example.myapplication.data.network
import com.example.myapplication.data.model.RegisterTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Header
import retrofit2.Response
interface RegisterTokenService {
    @POST("api/users/register-token")
    suspend fun sendToken(
        @Header("Authorization") authHeader:String,
        @Body request:RegisterTokenRequest

    ):Response<Unit>


}