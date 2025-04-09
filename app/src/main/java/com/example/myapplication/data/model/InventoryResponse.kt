package com.example.myapplication.data.model

data class InventoryResponse(
    val inventories: List<InventoryItem>,
    val total: Int,
    val totalPages: Int,
    val currentPage: Int
)
data class InventoryItem(
    val productName: String,
    val description: String,
    val quantity: Int,
    val location: String,
    val photoUrl: String ,
    val status: String,
    val userId: Int,
    val id: Int,
    val createdAt: String,
    val updatedAt: String
)