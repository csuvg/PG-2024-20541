package com.app.quauhtlemallan.ui.view.navbar.profile

import android.content.Context
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.app.quauhtlemallan.R
import com.app.quauhtlemallan.ui.theme.cinzelFontFamily
import com.app.quauhtlemallan.ui.view.navbar.BottomNavigationBar
import com.app.quauhtlemallan.ui.theme.forestGreen
import com.app.quauhtlemallan.ui.theme.crimsonRed
import com.app.quauhtlemallan.ui.theme.navyBlue
import com.app.quauhtlemallan.ui.viewmodel.SettingsViewModel
import com.app.quauhtlemallan.util.SessionManager
import com.app.quauhtlemallan.util.SettingsState
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.hbb20.CountryCodePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ProfileScreen(
    auth: FirebaseAuth,
    viewModel: SettingsViewModel,
    navController: NavHostController,
    googleSignInClient: GoogleSignInClient,
    sessionManager: SessionManager
) {
    val settingsState by viewModel.settingsStateFlow.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadUserData()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.uploadProfileImage(it) }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Contenedor para la imagen de perfil y el botón de cambiar foto
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                // Imagen de perfil
                Image(
                    painter = rememberImagePainter(viewModel.imageUrl),
                    contentDescription = "Imagen de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(0.5.dp, Color.Black, shape = CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        launcher.launch("image/*")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = navyBlue)
                ) {
                    Text(text = "Cambiar Foto", color = Color.White, fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal)
                }
            }

            // Campo de texto para el nombre de usuario
            OutlinedTextField(
                value = viewModel.username,
                onValueChange = viewModel::onUsernameChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text("Usuario", fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal) },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
            )

            // Campo de texto para el correo (deshabilitado)
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text("Correo", fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal) },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                enabled = false,
            )

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text("Contraseña", fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) painterResource(id = R.drawable.ic_visibility_off) else painterResource(id = R.drawable.ic_visibility)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = image, contentDescription = "Toggle password visibility")
                    }
                },
                shape = RoundedCornerShape(8.dp),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "País",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontFamily = cinzelFontFamily,
                    fontWeight = FontWeight.Normal
                )

                // Borde y estilo similar a OutlinedTextField para CountryCodePicker
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    CountryCodePickerView(
                        context = LocalContext.current,
                        selectedCountry = viewModel.selectedCountry,
                        onCountrySelected = { viewModel.onCountryChange(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Guardar Cambios
            Button(
                onClick = { viewModel.updateProfile() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = forestGreen)
            ) {
                Text(text = "Guardar Cambios", color = Color.White, fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal)
            }

            // Botón Cerrar Sesión
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        sessionManager.clearSession()
                        auth.signOut()
                        googleSignInClient.signOut().addOnCompleteListener {
                            LoginManager.getInstance().logOut()
                            navController.navigate("initial") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = crimsonRed)
            ) {
                Text(text = "Cerrar sesión", color = Color.White, fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal)
            }

            when (settingsState) {
                is SettingsState.Loading -> CircularProgressIndicator()
                is SettingsState.Error -> {
                    val errorMessage = (settingsState as SettingsState.Error).message
                    AlertDialog(
                        onDismissRequest = { viewModel.resetState() },
                        confirmButton = {
                            Button(onClick = { viewModel.resetState() }) {
                                Text("Aceptar", fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal)
                            }
                        },
                        title = { Text("Error", fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal) },
                        text = { Text(errorMessage, fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal) }
                    )
                }
                is SettingsState.Success -> {
                    Toast.makeText(LocalContext.current, "Recuerda guardar tus cambios antes de salir", Toast.LENGTH_SHORT).show()
                    viewModel.resetState()
                }
                else -> {}
            }
        }
    }
}

// Composable para integrar el CountryCodePicker de Android usando AndroidView
@Composable
fun CountryCodePickerView(
    context: Context,
    selectedCountry: String,
    onCountrySelected: (String) -> Unit
) {
    AndroidView(
        factory = {
            CountryCodePicker(context).apply {
                showFlag(true)
                showFullName(true)
                setShowPhoneCode(false)

                setCountryForNameCode(getCountryCodeByName(selectedCountry))

                setOnCountryChangeListener {
                    onCountrySelected(selectedCountryName)
                }
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
        },
        update = { view ->
            view.setCountryForNameCode(getCountryCodeByName(selectedCountry))
        }
    )
}

fun getCountryCodeByName(countryName: String): String {
    val countryList = Locale.getISOCountries()
    for (countryCode in countryList) {
        val locale = Locale("", countryCode)
        if (locale.displayCountry.equals(countryName, ignoreCase = true)) {
            return countryCode
        }
    }
    return "GT"
}