package com.example.pam_firestore.repository

import com.example.pam_firestore.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkRepositoryMhs(
    private val firestore: FirebaseFirestore
) : RepositoryMhs {
    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }

    override fun getAllMahasiswa(): Flow<List<Mahasiswa>> = callbackFlow{      //dengan riltime tanpa melakukan event
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nim",Query.Direction.ASCENDING)
            .addSnapshotListener{
                value, error ->
                if(value != null){
                    val mhsList = value.documents.mapNotNull {
                        //convert dari dokumen firestore ke data class
                        it.toObject(Mahasiswa::class.java)
                    }
                    //fungsi untuk mengirim collection ke db
                    trySend(mhsList)
                }
            }
        awaitClose{
            //menutup collection dari firestore
            mhsCollection.remove()
        }
    }

    override fun getMhs(nim: String): Flow<Mahasiswa> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }
}