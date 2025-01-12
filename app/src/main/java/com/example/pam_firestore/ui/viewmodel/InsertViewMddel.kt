package com.example.pam_firestore.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pam_firestore.model.Mahasiswa
import com.example.pam_firestore.repository.RepositoryMhs

class InsertViewMddel (
    private val mhs: RepositoryMhs
): ViewModel() {



}





// Data class variabel yang menyimpan data input form
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jeniskelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
)

