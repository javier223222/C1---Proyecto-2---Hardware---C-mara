package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.RegisterTokenRequest
import com.example.myapplication.data.network.RetrofitInstance
import com.example.myapplication.data.repository.LoginRepository
import com.example.myapplication.storage.TokenManager
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val tokenManager= TokenManager(application)
    var email=""
    var password=""
    private val _role=MutableStateFlow<String?>(null)
    val role:StateFlow<String?> = _role

    fun login(onSuccess:()->Unit,onError:(String)->Unit){
        viewModelScope.launch {
            try{
                val response= RetrofitInstance.authService.login(LoginRequest(email,password))
                val jwt=response.data.token
                val role=response.data.role
                tokenManager.saveToken(jwt)
                _role.value = role
                if(!role.equals("ADMIN", ignoreCase = true)){
                    FirebaseMessaging.getInstance().token.addOnSuccessListener { fmcToken->
                        viewModelScope.launch {
                            val result=RetrofitInstance.tokenService.sendToken(
                                "Bearer $jwt",
                                RegisterTokenRequest(fmcToken)
                            )
                            if (!result.isSuccessful) {
                                onError("FCM token error: ${result.code()}")
                            }

                        }
                    }
                }


                onSuccess()

            }catch (e:Exception){
                onError(e.localizedMessage?:"Login Error")
            }
        }
    }
fun getSavedToken():String?=tokenManager.getToken()

}