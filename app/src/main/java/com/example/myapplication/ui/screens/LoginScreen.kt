package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.LoginViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myapplication.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess:()->Unit

){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()


    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
          painter = painterResource(R.drawable.mangatori) ,
          contentDescription = "Anime Image",

          modifier = Modifier.fillMaxWidth()
      )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Inicio de sesión",
            fontFamily = FontFamily.Serif, // Cambia Serif por tu fuente personalizada
            fontSize = 30.sp,              // Tamaño de fuente
            fontWeight = FontWeight.Bold,  // Peso
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
   
        Button(onClick = {
            viewModel.login(email,password){
                if(it){
                    onLoginSuccess()

                }else{
                    Toast.makeText(context,"Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
                }
            }
        },
            modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(50),
            colors=ButtonDefaults.buttonColors(

                containerColor = Color(169, 196, 108), // Azul (RGB)
                contentColor = Color(0,0,0)
            ),
        ) {  Text("Login", color = Color.White) }
        TextButton(onClick = { /* Acción para recuperar contraseña */ }) {
            Text("¿Olvidaste tu contraseña?", color = Color.Blue)
        }
    }

}