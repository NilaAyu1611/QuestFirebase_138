package com.example.pam_firestore.repository

import com.example.pam_firestore.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {

        suspend fun insertMhs(mahasiswa: Mahasiswa)
        fun getAllMahasiswa(): Flow<List<Mahasiswa>>        // mendapatkan semua data mhs dlm bentuk aliran data flow

        fun getMhs(nim: String): Flow<Mahasiswa>            // mengambail data mhs berdasarkan NIM
        suspend fun deleteMhs(mahasiswa: Mahasiswa)         // menghapus data mhs
        suspend fun updateMhs(mahasiswa: Mahasiswa)         // // mengupdate data mhs




}