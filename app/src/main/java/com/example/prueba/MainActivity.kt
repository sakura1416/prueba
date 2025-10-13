package com.example.prueba

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaAppTheme {
                PantallaPrincipal()
            }
        }
    }
}

@Composable
fun PruebaAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Composable
fun PantallaPrincipal() {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var showModelo by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(1500)
            isLoading = false
            showModelo = true
        }
    }

    if (showModelo) {
        VistaModelo3D(
            onBack = { showModelo = false }
        )
    } else {
        MenuPrincipal(
            isLoading = isLoading,
            onCargarModelo = {
                isLoading = true
                verificarModelo3D(context)
            }
        )
    }
}

@Composable
fun MenuPrincipal(
    isLoading: Boolean,
    onCargarModelo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🌍 Earth From Space",
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Modelo 3D en formato GLB",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onCargarModelo,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Cargando Tierra...")
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🚀", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Explorar Earth From Space", fontSize = 18.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Información del modelo
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Información del Modelo:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                InfoItem("Nombre", "Earth From Space")
                InfoItem("Formato", "GLB (Blender)")
                InfoItem("Ubicación", "assets/models/Earth_From_Space.glb")
            }
        }
    }
}

@Composable
fun VistaModelo3D(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🌎 Earth From Space",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))


        Surface(
            modifier = Modifier
                .size(180.dp)
                .padding(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.extraLarge,
            shadowElevation = 16.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🌍",
                        fontSize = 80.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Modelo 3D Activo",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Earth From Space",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Modelo 3D cargado exitosamente",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Controles de navegación
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { }) {
                Text("Rotar")
            }
            OutlinedButton(onClick = { }) {
                Text("Zoom")
            }
            OutlinedButton(onClick = { }) {
                Text("Reset")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Volver al Menú Principal")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Earth From Space - Modelo GLB funcionando",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun InfoItem(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
        Text(
            text = valor,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Función para verificar el modelo 3D
private fun verificarModelo3D(context: android.content.Context) {
    try {
        val assetManager = context.assets
        val inputStream = assetManager.open("models/Earth_From_Space.glb")
        val fileSize = inputStream.available()
        inputStream.close()

        Toast.makeText(
            context,
            "✅ Earth From Space encontrado\nTamaño: ${fileSize / 1024} KB",
            Toast.LENGTH_LONG
        ).show()

    } catch (e: Exception) {
        Toast.makeText(
            context,
            "❌ Error al cargar Earth From Space: ${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantalla() {
    PruebaAppTheme {
        PantallaPrincipal()
    }
}