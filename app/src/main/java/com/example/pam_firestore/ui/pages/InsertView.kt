package com.example.pam_firestore.ui.pages

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_firestore.ui.viewmodel.FormState
import com.example.pam_firestore.ui.viewmodel.InsertViewModel
import com.example.pam_firestore.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState // State utama untuk loading, succes, Error
    val uiEvent = viewModel.uiEvent // state untuk form dan validasi
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan state untuk snackbar dan navigasi
    LaunchedEffect(uiState) {
        when(uiState) {
            is FormState.Success -> {
                println(
                    "InsertMhsView: uiState is FromState.Success, navigate to home" + uiState.message
                )
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message) // Tampilkan snackbar
                }
                delay(700)
                onNavigate() // Navigasi langsung

                viewModel.resetSnackBarMessage()    // reset snackbar state
            }

            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }
            else -> Unit
        }
    }

}