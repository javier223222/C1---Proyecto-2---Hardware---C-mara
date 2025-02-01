package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog



@Composable
fun  AddProductDialog(
    onDismiss:()->Unit,
    onAddProduct:(String,String,Int,String)->Unit

){
    var name by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var urlimage by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),) {
            Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Agregar Producto")
                TextField(value = name, onValueChange = {name=it}, label = { Text("Nombre") } )
                TextField(value = descripcion, onValueChange = {descripcion=it}, label = { Text("Descripcion") } )
                TextField(value = precio, onValueChange = {precio=it}, label = { Text("Precio") } )
                TextField(value = urlimage, onValueChange = {urlimage=it}, label = { Text("Url de imagen") } )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val priceValue=precio.toIntOrNull()
                            if(name.isNotEmpty() && descripcion.isNotEmpty() && priceValue!=null && urlimage.isNotEmpty() ){
                                onAddProduct(name,descripcion,priceValue,urlimage)
                                onDismiss()
                            }
                        },
                        colors=ButtonDefaults.buttonColors(

                            containerColor = Color(169, 196, 108), // Azul (RGB)
                            contentColor = Color(0,0,0)
                        ),
                    ) {
                        Text("Agregar")
                    }
                }
            }
        }
    }
}
