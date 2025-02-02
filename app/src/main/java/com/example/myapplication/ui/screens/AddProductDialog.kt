import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onAddProduct: (String, String, Int, File) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageFile by remember { mutableStateOf<File?>(null) }
    var isError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Permisos para cámara y almacenamiento
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
                imageFile = File(it.path ?: "")
            }
        } else {
            Toast.makeText(context, "Error al capturar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Agregar Producto",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del producto") },
                    isError = isError && name.isEmpty(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    isError = isError && descripcion.isEmpty(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    isError = isError && precio.isEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 📸 Sección para tomar imagen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp))
                        .clickable {
                            val newFile = createImageFile(context)
                            val uri = FileProvider.getUriForFile(
                                context,
                                "com.example.myapplication.fileprovider",
                                newFile
                            )

                            imageFile = newFile
                            imageUri = uri
                            cameraLauncher.launch(uri)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUri),
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_),
                                contentDescription = "Tomar foto",
                                tint = Color.Gray,
                                modifier = Modifier.size(80.dp)
                            )
                            Text(
                                text = "Toca para agregar imagen",
                                fontSize = 14.sp,
                                color = Color(0xFF666666)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 📌 Botón para agregar producto
                Button(
                    onClick = {
                        val priceValue = precio.toIntOrNull()
                        if (name.isNotEmpty() && descripcion.isNotEmpty() && priceValue != null && imageFile != null) {
                            onAddProduct(name, descripcion, priceValue, imageFile!!)
                            onDismiss()
                        } else {
                            isError = true
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    Text("Agregar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// 🔹 Función para crear un archivo de imagen en almacenamiento accesible
fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    if (storageDir != null && !storageDir.exists()) {
        storageDir.mkdirs()
    }

    return File(storageDir, "IMG_${timeStamp}.jpg")
}
