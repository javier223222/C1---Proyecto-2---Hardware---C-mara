package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Product

@Composable
fun ProductItem(
    product: Product,
    onEditProduct: (Product) -> Unit,
    onDeleteProduct: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {

            // 📌 Imagen del producto (ocupa todo el ancho)
            AsyncImage(
                model = product.image,
                contentDescription = "Imagen de ${product.image}",
                contentScale = ContentScale.Crop,  // 📌 La imagen llena todo el espacio disponible
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            // 📌 Contenido del producto
            Column(modifier = Modifier.padding(16.dp)) {

                // 📌 Precio
                Text(
                    text = "Precio: $${product.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 📌 Descripción
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 📌 Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onEditProduct(product) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(169, 196, 108),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Editar", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = { onDeleteProduct(product.id) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(255, 71, 87), // Rojo para eliminar
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Eliminar", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}


