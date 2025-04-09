package com.example.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.InventoryItem
import com.example.myapplication.data.model.InventoryResponse
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.network.RetrofitInstance
import com.example.myapplication.storage.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)

    private val _products = MutableStateFlow<List<InventoryItem>>(emptyList())
    val products: StateFlow<List<InventoryItem>> = _products


    init {
        fetchInventory()
    }

    private fun getToken(): String? = tokenManager.getToken()

    fun fetchInventory() {
        val token = getToken() ?: return
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.inventoryService.getmineinventary("Bearer $token")
                Log.d("respuesta", "fetchInventory: $response")
                _products.value = response.inventories

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun addProduct(productname:String,description:String,quantity:Int,location:String,photo:File){
        val token=getToken()?:return
        viewModelScope.launch {
            try{
                val resquestBody=photo.asRequestBody("image/*".toMediaTypeOrNull())
                val multiPartImage=MultipartBody.Part.createFormData("photo",photo.name,resquestBody)
                val newProduct=RetrofitInstance.inventoryService.createInventary("Bearer $token",
                    productname.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description.toRequestBody("text/plain".toMediaTypeOrNull()),
                    quantity.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    location.toRequestBody("text/plain".toMediaTypeOrNull()),
                    multiPartImage


                    )
                val pr=_products.value+newProduct
                _products.value=pr
                fetchInventory()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    fun editProduct(productId: Int,productname:String,description:String,quantity:Int,location:String,photo:File){
        val token=getToken()?:return
        viewModelScope.launch {
            try{
                val resquestBody=photo.asRequestBody("image/*".toMediaTypeOrNull())
                val multiPartImage=MultipartBody.Part.createFormData("photo",photo.name,resquestBody)
                val newProduct=RetrofitInstance.inventoryService.updateInventoryProductName(productId,"Bearer $token",
                    productname.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description.toRequestBody("text/plain".toMediaTypeOrNull()),
                    quantity.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    location.toRequestBody("text/plain".toMediaTypeOrNull()),
                    multiPartImage


                )

                fetchInventory()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    fun deleteProduct(productId: Int) {
        val token=getToken()?:return
        viewModelScope.launch {
            try {
                // ⚠️ ACTUALIZAR LA LISTA MANUALMENTE ANTES DE LLAMAR `getProducts()`
                val updatedList = _products.value.filter { it.id != productId }
                _products.value = updatedList
               RetrofitInstance.inventoryService.deleteInventory(productId,"Bearer $token")

                fetchInventory()


            } catch (e: Exception) {
                println("Error al eliminar producto: ${e.message}")
            }
        }
    }



}
