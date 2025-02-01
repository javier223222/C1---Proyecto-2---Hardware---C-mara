package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.model.Product
import com.example.myapplication.viewmodel.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ProductListScreen(viewModel: ProductListViewModel = viewModel()) {
    val products by viewModel.products.collectAsState()
    val context = LocalContext.current
    var showEditDialog by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getProducts() // ⚠️ Cargar productos al inicio
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Lista de Mangas") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }, // ⚠️ Mostrar modal para agregar producto

            ) {

            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onEditProduct = {
                        selectedProduct = it
                        showEditDialog = true
                    },
                    onDeletePrduct = { productId ->
                        viewModel.deleteProduct(productId) // ⚠️ Borrar producto y actualizar lista
                    }
                )
            }
        }
    }

    if (showEditDialog && selectedProduct != null) {
        EditProductDialog(
            product = selectedProduct!!,
            onDismiss = { showEditDialog = false },
            onUpdateProduct = { newName ->
                selectedProduct?.let {
                    viewModel.updateProduct(it.id, newName)
                }
            }
        )
    }

    if (showAddDialog) {
        AddProductDialog(
            onDismiss = { showAddDialog = false },
            onAddProduct = { name, description, price, imageUrl ->
                viewModel.addProduct(name,description,imageUrl,price)
            }
        )
    }
}

