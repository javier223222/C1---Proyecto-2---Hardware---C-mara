package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.viewmodel.InventoryViewModel
import com.example.myapplication.data.model.InventoryItem
import com.example.myapplication.data.model.Product

@Composable
fun InventoryScreen(viewModel: InventoryViewModel) {


    val products by viewModel.products.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<InventoryItem?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName=GoogleFont("Poppins")
    val fontFamily= FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )
    LaunchedEffect(Unit) {
        viewModel.fetchInventory()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Inventory",
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Color.Black

            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable {
                            viewModel.fetchInventory()
                        }

                ){

                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable {
                            showAddDialog=true
                        }

                ){

                }


            }
        }

        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(16.dp))

        if(products.isNotEmpty()){
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                items(products) { item ->
                    InventoryCard(item,
                        onEditProduct = {
                            selectedProduct=it
                            showEditDialog=true
                        },
                        onDeleteProduct = { porductId->
                            viewModel.deleteProduct(porductId)

                        })

                }
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        if(showAddDialog){
            AddInventory(
                onDismiss = {showAddDialog=false},
                onAddProduct = {
                    productname,description,quantity,location,photo->
                    viewModel.addProduct(productname,description,quantity,location,photo)
                }
            )
        }
        if(showEditDialog && selectedProduct!=null){
            EditInventoryDialog(
                inventary = selectedProduct!!,
                onDismiss = { showEditDialog = false },
                onUpdateProduct = {
                        productname,description,quantityValue,location, photo ->
                        selectedProduct?.let {
                            viewModel.editProduct(it.id,productname,description,quantityValue,location, photo)
                        }

                }

            )
        }





    }
}

@Composable
fun CircleIconPlaceholder(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    )
}

@Composable
fun InventoryCard(item: InventoryItem, onEditProduct: (InventoryItem) -> Unit,onDeleteProduct: (Int) -> Unit) {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName=GoogleFont("Poppins")
    val fontFamily= FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )

    Row(
        modifier = Modifier
            .width(360.dp)
            .clip(RoundedCornerShape(16.dp))
            .height(152.dp)
            .background(Color(0xFFE3E3E3))
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = item.photoUrl,
            contentDescription = item.productName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(110.dp)
                .height(136.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
                      .width(218.dp)
                       .clip(RoundedCornerShape(16.dp)).
                       background(Color(0xFFFFFDFD))
                      .height(136.dp)
                .padding(horizontal = 12.dp, vertical =8.dp )



        ) {
            Text(
                text = item.productName,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = item.description,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = "Ubicacion: ${item.location}",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = "In stock: ${item.quantity}",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Color.Black
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable {
                            onDeleteProduct(item.id)

                        }

                ){

                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable {
                            onEditProduct(item)
                        }

                ){

                }


            }
        }

    }
}