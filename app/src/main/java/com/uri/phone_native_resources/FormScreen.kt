package com.uri.phone_native_resources

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.android.gms.location.LocationServices
import java.io.File
import java.util.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FormScreen() {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var savedFormData by remember { mutableStateOf<List<FormData>>(emptyList()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { }
    )

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") }
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        TextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Comentário") }
        )

        Button(onClick = {
            if (checkAndRequestPermissions(context)) {
                val file = File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "${UUID.randomUUID()}.jpg"
                )
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                imageUri = uri
                launcher.launch(uri)
            }
        }) {
            Text("Tirar Foto")
        }

        imageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(data = uri),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
        }


        Button(onClick = {
            saveFormData(context, name, email, comment, imageUri?.toString() ?: "")
        }) {
            Text("Salvar Dados")
        }

        Button(onClick = {
            val dbHelper = FormDatabaseHelper(context)
            savedFormData = dbHelper.getAllFormData()
        }) {
            Text("Ver Dados Salvos")
        }

        savedFormData.forEach { formData ->
            Text("Nome: ${formData.name}, Email: ${formData.email}")
            Text("Comentário: ${formData.comment}")
            Text("Caminho da Imagem: ${formData.imagePath}")
        }

        Button(onClick = {
            getCurrentLocation(context) { loc ->
                loc?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                }
            }
        }) {
            Text("Mostrar Localização")
        }

        latitude?.let { lat ->
            longitude?.let { lon ->
                Text("Latitude: $lat, Longitude: $lon")
            }
        }
    }
}

fun checkAndRequestPermissions(context: Context): Boolean {
    val cameraPermission = Manifest.permission.CAMERA
    val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    val permissionsToRequest = mutableListOf<String>()

    if (ContextCompat.checkSelfPermission(context, cameraPermission) != PackageManager.PERMISSION_GRANTED) {
        permissionsToRequest.add(cameraPermission)
    }

    if (ContextCompat.checkSelfPermission(context, storagePermission) != PackageManager.PERMISSION_GRANTED) {
        permissionsToRequest.add(storagePermission)
    }

    if (ContextCompat.checkSelfPermission(context, locationPermission) != PackageManager.PERMISSION_GRANTED) {
        permissionsToRequest.add(locationPermission)
    }

    return permissionsToRequest.isEmpty()
}

fun getCurrentLocation(context: Context, onLocationReceived: (android.location.Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            onLocationReceived(location)
        }
    } else {
        onLocationReceived(null)
    }
}

fun saveFormData(context: Context, name: String, email: String, comment: String, imagePath: String) {
    val dbHelper = FormDatabaseHelper(context)
    dbHelper.insertFormData(name, email, comment, imagePath)
}
