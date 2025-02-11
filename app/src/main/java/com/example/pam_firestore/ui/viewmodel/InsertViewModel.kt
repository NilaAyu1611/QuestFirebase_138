package com.example.pam_firestore.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_firestore.model.Mahasiswa
import com.example.pam_firestore.repository.RepositoryMhs
import kotlinx.coroutines.launch

class InsertViewModel (
    private val mhs: RepositoryMhs
): ViewModel() {
    // Menyimpan status terkini uiEvent
    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set

    // mewakili sttutus formstate
    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    //Memperbarui state berdasarkan input user
    fun updateState (mahasiswaEvent: MahasiswaEvent){
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent,
        )
    }

    // validasi data input user
    fun validateFields(): Boolean{
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState (
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            gender = if (event.gender.isNotEmpty()) null else "Gender tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong"

        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertMhs() {
        if (validateFields()) {     // Memvalidasi input pengguna
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    // Menginsert data mahasiswa ke repository
                    mhs.insertMhs(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data berhasil disimpan")
                } catch (e: Exception) {
                    uiState = FormState.Error ("Data gagal disimpan")
                }
            }
        } else {
            uiState = FormState.Error("Data tidak valid")
        }
    }
    fun resetForm(){
        uiEvent = InsertUiState()       // Mengatur ulang UI event ke state awal
        uiState = FormState.Idle        // Mengatur ulang status UI ke Idle
    }

    fun resetSnackBarMessage() {
        uiState = FormState.Idle
    }
}

//cara yang terstruktur dan aman untuk mengelola berbagai status
sealed class FormState {
    object Idle: FormState()    // Menandakan bahwa form dalam keadaan tidak aktif
    object Loading: FormState()
    data class Success (val message: String) : FormState()
    data class Error (val message: String) : FormState()
}

// menyimpan status event terkait dengan proses penyisipan data mahasiswa
data class InsertUiState (
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),       // meyimpan event terkait input mahasiswa
    val isEntryValid: FormErrorState = FormErrorState(),        // menyimpan statyus validasi dari form input mahasiswa
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val gender: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
){
    fun isValid(): Boolean {
        return nim == null && nama == null && gender == null &&
                alamat == null && kelas ==  null && angkatan == null
    }
}


// Data class variabel yang menyimpan data input form
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val gender: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
)

// Menyimpan input form ke dalam entry
fun MahasiswaEvent.toMhsModel(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    gender = gender,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)
