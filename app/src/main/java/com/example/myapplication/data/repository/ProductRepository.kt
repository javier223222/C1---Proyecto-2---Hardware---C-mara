package com.example.myapplication.data.repository

import com.example.myapplication.data.network.ApiClient
import com.example.myapplication.data.network.ProductDbService
import com.example.myapplication.data.model.Product


class ProductRepository {
   private val api:ProductDbService=ApiClient.retrofit.create(ProductDbService::class.java)

    suspend  fun getProducts(): List<Product>{

        return api.getProducts()

    }

    suspend  fun getProduct(id:Int): Product {
        return api.getProduct(id)


    }



    suspend fun createProduct(name:String,description:String,image:String,price:Int): Product {
        return api.createProduct(Product(0,name,description,price,image,"",""))


    }


    suspend fun updateProduct(id:Int,product: Product): Product {
        return api.updateProduct(id,product)

    }


    suspend fun deleteProduct(id:Int): Boolean{
        return  api.deleteProduct(id)

    }



}