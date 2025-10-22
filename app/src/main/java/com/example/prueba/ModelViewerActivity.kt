package com.example.prueba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.sceneview.Scene
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader

class ModelViewerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ModelViewerScreen()
            }
        }
    }
}

@Composable
fun ModelViewerScreen() {
    var isLoading by remember { mutableStateOf(true) }
    var modelNode by remember { mutableStateOf<ModelNode?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)

    // Cargar el modelo en el hilo principal con LaunchedEffect
    LaunchedEffect(Unit) {
        try {
            // Crear instancia del modelo (debe ser en el hilo principal)
            val instance = modelLoader.createModelInstance(
                assetFileLocation = "models/earth.glb"
            )

            if (instance != null) {
                modelNode = ModelNode(
                    modelInstance = instance,
                    scaleToUnits = 1.5f,
                    centerOrigin = io.github.sceneview.math.Position(0f, 0f, 0f)
                ).apply {
                    // Posicionar el modelo frente a la cámara
                    position = io.github.sceneview.math.Position(x = 0f, y = 0f, z = -4f)
                }
            } else {
                errorMessage = "No se pudo crear la instancia del modelo"
            }
        } catch (e: Exception) {
            errorMessage = "Error al cargar: ${e.message}"
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Mostrar la escena 3D
        if (modelNode != null) {
            Scene(
                modifier = Modifier.fillMaxSize(),
                engine = engine,
                modelLoader = modelLoader,
                childNodes = listOf(modelNode!!)
            )
        }

        // Indicador de carga
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = Color.White
                    )
                    Text(
                        text = "Cargando modelo 3D...",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        // Mensaje de error
        if (errorMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "❌ Error",
                        color = Color.Red,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage ?: "Error desconocido",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}