package com.example.myapplication.ui.screens

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import java.io.File
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.myapplication.data.repository.showNotification
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@Composable
fun AddInventory(
    onDismiss:()->Unit,
    onAddProduct:(String, String, Int, String, File)->Unit

){
    var productname by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageFile by remember { mutableStateOf<File?>(null) }
    var isError by remember { mutableStateOf(false) }
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName= GoogleFont("Poppins")
    val fontFamily= FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )

    val context = LocalContext.current

    val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val storageGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions[Manifest.permission.READ_MEDIA_IMAGES] ?: false
        } else {
            permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        }

        if (!cameraGranted || !storageGranted) {
            Toast.makeText(context, "Debes conceder permisos para usar la cámara", Toast.LENGTH_SHORT).show()
        }
    }


    LaunchedEffect(Unit) {
        permissionLauncher.launch(permissionsToRequest)
    }


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri?.let {
                imageFile = getFileFromContentUri(context, it)
            }
        } else {
            Toast.makeText(context, "Error al capturar la imagen", Toast.LENGTH_SHORT).show()
        }
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            imageFile = getFileFromContentUri(context, it)
            isError = false
        }
    }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation(context) { currentAddress ->
                location = currentAddress
            }
        } else {
            Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }


    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier=Modifier
                .padding(20.dp).
                animateContentSize()
                .width(650.dp)
                .height(1150.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(12.dp)




        ){
            Text(
                text = "Inventory",
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Color.Black

            )
          Column(modifier = Modifier.padding(24.dp) ,verticalArrangement = Arrangement.spacedBy(14.dp)) {
              OutlinedTextField(
                  value = productname,
                  onValueChange = {
                      productname = it

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
                  label = { Text(text="Nombre", fontFamily = fontFamily) },
                  modifier = Modifier.fillMaxWidth(),
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
              )

              Spacer(modifier = Modifier.height(16.dp))
              OutlinedTextField(
                  value = quantity,
                  onValueChange = {
                      quantity = it

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
                  label = { Text(text="Cantidad", fontFamily = fontFamily) },
                  modifier = Modifier.fillMaxWidth(),
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
              )
              Spacer(modifier = Modifier.height(16.dp))
              OutlinedTextField(
                  value = location,
                  onValueChange = {
                      location =it

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
                  label = { Text(text="Ubicacion", fontFamily = fontFamily) },
                  modifier = Modifier.fillMaxWidth(),
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
              )
              Spacer(modifier = Modifier.height(16.dp))
              Button(
                  onClick = {
                      locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

                  },
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBC5555)),
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(50.dp),
                  shape = MaterialTheme.shapes.large
              ) {
                  Text("Usar Ubicacion Actual", fontFamily = fontFamily, color = Color.White)
              }
              Spacer(modifier = Modifier.height(10.dp))
              OutlinedTextField(
                  value = description,
                  onValueChange = {
                      description =it

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
                  label = { Text(text="Description", fontFamily = fontFamily) },
                  modifier = Modifier.fillMaxWidth(),
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
              )
              Spacer(modifier = Modifier.height(20.dp))
              Button(
                  onClick = {
                      galleryLauncher.launch("image/*")


                  },
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB8B8B8)),
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(50.dp),
                  shape = MaterialTheme.shapes.large
              ) {
                  Text("subir foto", fontFamily = fontFamily, color = Color.White)
              }
              Spacer(modifier = Modifier.height(10.dp))

              Button(
                  onClick = {
                      val newFile = createImageFile(context)
                      val contentUri = FileProvider.getUriForFile(
                          context,
                          "com.example.myapplication.fileprovider",
                          newFile
                      )
                      imageUri = contentUri
                      // Inicia la cámara
                      cameraLauncher.launch(contentUri)

                  },
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C6C6C)),
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(50.dp),
                  shape = MaterialTheme.shapes.large
              ) {
                  Text("Tomar foto", fontFamily = fontFamily, color = Color.White)
              }
              Spacer(modifier = Modifier.height(16.dp))
              Button(
                  onClick = {
                      val quantityValue=quantity.toIntOrNull()
                      if(productname.isNotEmpty() && description.isNotEmpty() &&quantityValue!=null && location.isNotEmpty() && imageFile!=null ){
                          onAddProduct(productname,description,quantityValue,location, imageFile!!)
                          showNotification(context,productname)
                          onDismiss()

                      }else{
                          isError=true
                          Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()

                      }


                  },
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C6C6C)),
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(50.dp),
                  shape = MaterialTheme.shapes.large
              ) {
                  Text("Agregar", fontFamily = fontFamily, color = Color.White)
              }













          }
        }

    }






}


@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationRetrieved: (String) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            location?.let {
                val geocoder = Geocoder(context)
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                val address = addresses?.get(0)?.getAddressLine(0) ?: "${it.latitude}, ${it.longitude}"
                onLocationRetrieved(address)
            }
        }
}





