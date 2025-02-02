package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.LoginViewModel

import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    Image(
        painter = painterResource(id=R.drawable.mangatori),
        contentDescription = "Imagen de fondo",

        modifier = Modifier.fillMaxWidth()
             // 🔹 Ajuste de altura para mejor proporción



    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Inicio de sesión",
            fontFamily = FontFamily.Serif, // Cambia Serif por tu fuente personalizada
            fontSize = 28.sp,              // Tamaño de fuente
            fontWeight = FontWeight.Bold,  // Peso
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF333333),
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico", color = Color(0xFF666666))},
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
            ,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0057D9), // 🔹 Azul vibrante para resaltar
                unfocusedBorderColor = Color(0xFFB3D4FF), // 🔹 Azul claro más suave
                cursorColor = Color(0xFF0057D9)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo de texto para contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = Color(0xFF666666)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0057D9),
                unfocusedBorderColor = Color(0xFFB3D4FF),
                cursorColor = Color(0xFF0057D9)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
                onClick = {
                    viewModel.login(email, password) {
                        if (it) {
                            onLoginSuccess()
                        } else {
                            Toast.makeText(context, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(169, 196, 108),
                    contentColor = Color.Black
                )
            ) { Text("Login", color = Color.White) }


    }

//        // Imagen de fondo
//        Image(
//            painter = painterResource(R.drawable.mangatori),
//            contentDescription = "Anime Image",
//
//         // 🔹 Asegura que se ajuste sin deformarse
//        )
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(32.dp))
//
//            Text(
//                text = "Inicio de sesión",
//                fontFamily = FontFamily.Serif,
//                fontSize = 30.sp,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(8.dp),
//                color = Color.White // 🔹 Color blanco para contraste con la imagen
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Campo de texto para correo electrónico
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Correo electrónico", color = Color.White) }, // 🔹 Color para contraste
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Email,
//                    imeAction = ImeAction.Next
//                ),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color.White,
//                    unfocusedBorderColor = Color.Gray,
//                    cursorColor = Color.White
//                )
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Campo de texto para contraseña
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Contraseña", color = Color.White) },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth(),
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Password,
//                    imeAction = ImeAction.Done
//                ),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color.White,
//                    unfocusedBorderColor = Color.Gray,
//                    cursorColor = Color.White
//                )
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    viewModel.login(email, password) {
//                        if (it) {
//                            onLoginSuccess()
//                        } else {
//                            Toast.makeText(context, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth().height(48.dp),
//                shape = RoundedCornerShape(50),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(169, 196, 108),
//                    contentColor = Color.Black
//                )
//            ) { Text("Login", color = Color.White) }
//
//            TextButton(onClick = { /* Acción para recuperar contraseña */ }) {
//                Text("¿Olvidaste tu contraseña?", color = Color.White)
//            }
//        }
//    }
}
