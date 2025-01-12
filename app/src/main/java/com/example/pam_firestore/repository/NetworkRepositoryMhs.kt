package com.example.pam_firestore.repository

import com.example.pam_firestore.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

// Kelas ini bertanggung jawab untuk berinteraksi dengan Firestore untuk data Mahasiswa
class NetworkRepositoryMhs(
    private val firestore: FirebaseFirestore
) : RepositoryMhs {

    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        try {
            // Menambahkan objek mahasiswa ke koleksi "Mahasiswa" di Firestore
            firestore.collection("Mahasiswa").add(mahasiswa).await()
        } catch (e: Exception){
            // exception jika terjadi kesalahan saat menambahkan data
            throw Exception("Gagal menambahkan data mahasiswa : ${e.message}")
        }
    }

    override fun getAllMahasiswa(): Flow<List<Mahasiswa>> = callbackFlow{      //dengan riltime tanpa melakukan event
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nim",Query.Direction.DESCENDING)
            .addSnapshotListener{
                value, error ->
                if(value != null){
                    val mhsList = value.documents.mapNotNull {
                        //convert dari dokumen firestore ke data class
                        it.toObject(Mahasiswa::class.java)
                    }
                    //fungsi untuk mengirim collection ke db
                    trySend(mhsList) // try send memberikan fungsi untuk mengirim data ke flow
                }
            }
        awaitClose{
            //menutup collection dari firestore
            mhsCollection.remove()
        }
    }

    override fun getMhs(nim: String): Flow<Mahasiswa> = callbackFlow{
        // Mengakses dokumen mahasiswa berdasarkan NIM dari koleksi "Mahasiswa"
        val mhsDocument = firestore.collection("Mahasiswa")
            .document(nim)
            .addSnapshotListener { value, error ->
                if (value != null){
                    val mhs = value.toObject(Mahasiswa::class.java) !!
                }
            }
        // mentup listener saat flow tidak lagi digunakan
        awaitClose{
            mhsDocument.remove()        // menghapus listener untuk dokumen mhs
        }
    }

    override suspend fun deleteMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }
}