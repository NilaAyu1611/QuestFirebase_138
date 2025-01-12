package com.example.pam_firestore.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pam_firestore.MahasiswaApp
import com.example.pam_firestore.model.Mahasiswa
import com.example.pam_firestore.repository.RepositoryMhs

class InsertViewMddel (
    private val mhs: RepositoryMhs
): ViewModel() {



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
