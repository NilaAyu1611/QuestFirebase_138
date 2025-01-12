package com.example.pam_firestore.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_firestore.model.Mahasiswa
import com.example.pam_firestore.repository.RepositoryMhs
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repoMhs: RepositoryMhs
): ViewModel(){
    var mhsUIState:HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs(){
        viewModelScope.launch {
            repoMhs.getAllMahasiswa().onStart {
                mhsUIState = HomeUiState.Loading
            }
                .catch {
                    mhsUIState = HomeUiState.Error(e = it)
                }
                .collect{
                    mhsUIState = if (it.isEmpty()){             //it berrti list mhs
                        HomeUiState.Error(Exception("Belum ada data mahasiswa"))
                    }
                    else{
                        HomeUiState.Success(it)
                    }
                }
        }
    }
}

sealed class HomeUiState{
    //Loading
    object Loading : HomeUiState()
    //Sukses (mengembalikan data list mhs
    data class Success(val data: List<Mahasiswa>) : HomeUiState()
    //Error
    data class Error(val e: Throwable) : HomeUiState()
}