package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Product


@Composable
fun ProductItem(
    product: Product,
    onEditProduct: (Product)->Unit,
    onDeletePrduct:(Int)->Unit
){
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(8.dp)

    ) {

        Column (modifier = Modifier.fillMaxWidth().padding(16.dp)){
            AsyncImage(
                model = product.image,
                contentDescription = "Imagen de ${product.image}",
                modifier = Modifier.fillMaxWidth().height(150.dp)


            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Precio $ ${product.price}",
                fontFamily = FontFamily.Serif, // Cambia Serif por tu fuente personalizada
                fontSize = 10.sp,              // Tamaño de fuente
                fontWeight = FontWeight.Bold,  // Peso
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Descripcion  ${product.description}",
                fontFamily = FontFamily.Serif, // Cambia Serif por tu fuente personalizada
                fontSize = 10.sp,              // Tamaño de fuente
                fontWeight = FontWeight.Bold,  // Peso
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )


        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){

            Button(onClick = {onEditProduct(product)},  colors = ButtonDefaults.buttonColors(
                containerColor = Color(169, 196, 108), // Azul (RGB)
                contentColor = Color(0,0,0)
            ) ){
                Text("Editar")
            }
            Button(onClick = { onDeletePrduct(product.id) }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(169, 196, 108), // Azul (RGB)
                contentColor = Color(0,0,0)
            )) {
                Text("Eliminar")
            }
        }

    }

}
