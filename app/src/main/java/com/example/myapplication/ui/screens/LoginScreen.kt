package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.TextAlign


import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.R

@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName=GoogleFont("Poppins")
    val fontFamily= FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )
    val fontNameSecundary=GoogleFont("Montserrat")
    val fontFamilySecundary= FontFamily(
        Font(googleFont = fontNameSecundary, fontProvider = provider)
    )


    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.background_opticare),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Logo centered

        Image(
            painter = painterResource(id = R.drawable.logo_opticare),
            contentDescription = "Opticare Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)  // 🔥 Centra horizontalmente el logo completo
                .padding(top = 200.dp)
                .size(width = 600.dp, height = 100.dp)
        )





        Column(


            modifier = Modifier
                    .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)

                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(

                text = "Inicia Sesion",
                fontFamily = fontFamily,

                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(), //
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Color.Black
            )

            Text(
                text = "Discover how to manage all your products all in one place",
                fontFamily = fontFamilySecundary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    viewModel.email = it
                },
                shape= RoundedCornerShape(
                    24.dp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF5A5A5A),
                    unfocusedBorderColor = Color(0xFF5A5A5A),
                    cursorColor = Color(0xFF5A5A5A),
                    focusedLabelColor = Color(0xFF5A5A5A),
                    unfocusedLabelColor = Color(0xFF5A5A5A)
                ),
                label = { Text(text="Email", fontFamily = fontFamily) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    viewModel.password = it
                },
                shape= RoundedCornerShape(
                    24.dp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF5A5A5A),
                    unfocusedBorderColor = Color(0xFF5A5A5A),
                    cursorColor = Color(0xFF5A5A5A),
                    focusedLabelColor = Color(0xFF5A5A5A),
                    unfocusedLabelColor = Color(0xFF5A5A5A)
                ),
                label = { Text("Password", fontFamily = fontFamily) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(
                        onSuccess = onLoginSuccess,
                        onError = { error = it }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBC5555)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Entrar", fontFamily = fontFamily, color = Color.White)
            }

            error?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = Color.Red, fontFamily = fontFamily)
            }
        }
    }
}
