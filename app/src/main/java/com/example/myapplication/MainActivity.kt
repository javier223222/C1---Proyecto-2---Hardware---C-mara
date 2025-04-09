package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.*
import com.example.myapplication.data.repository.PreferencesManager
import com.example.myapplication.data.repository.createNotifiChannel
import com.example.myapplication.ui.screens.InventoryScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.ProductListScreen
import com.example.myapplication.viewmodel.InventoryViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController= rememberNavController()
            val loginViewModel:LoginViewModel= viewModel()
            val inventoryViewModel:InventoryViewModel= viewModel()
            val role by loginViewModel.role.collectAsState()
            NavHost(navController=navController, startDestination = "login"){
                composable("login"){
                    LoginScreen(viewModel = loginViewModel,onLoginSuccess = {

                    })
                }
                composable("admin_dashboard") {
                    // Aquí va tu vista de admin
                   // ← Puedes reemplazar esto con tu composable de admin
                }

                composable("seller_dashboard") {
                    // Aquí va tu vista de vendedor
                    InventoryScreen(viewModel = inventoryViewModel) // ← Reemplaza con tu UI para vendedores
                }

            }
            LaunchedEffect(role) {
                when(role){
                    "ADMIN"->navController.navigate("admin_dashboard"){
                        popUpTo("login"){inclusive=true}
                    }
                    "VENDEDOR"->navController.navigate("seller_dashboard"){
                        popUpTo("login"){inclusive=true}
                    }
                }
            }

        }
    }
}


