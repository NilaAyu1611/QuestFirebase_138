package com.example.pam_firestore.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pam_firestore.MahasiswaApp
import com.example.pam_firestore.model.Mahasiswa
import com.example.pam_firestore.repository.RepositoryMhs

class InsertViewMddel (
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
