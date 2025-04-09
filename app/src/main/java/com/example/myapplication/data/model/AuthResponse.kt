package com.example.myapplication.data.model

data class AuthResponse(
    val message:String,
    val data:AuthData
)

data class AuthData(
    val token:String,
    val role:String
)