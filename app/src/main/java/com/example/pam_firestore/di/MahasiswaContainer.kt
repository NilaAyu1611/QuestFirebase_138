package com.example.pam_firestore.di

import android.content.Context
import com.example.pam_firestore.repository.NetworkRepositoryMhs
import com.example.pam_firestore.repository.RepositoryMhs
import com.google.firebase.firestore.FirebaseFirestore


interface InterfaceContainerApp{
    val repositoryMhs:RepositoryMhs
}

class MahasiswaContainer(private val context: Context) : InterfaceContainerApp {
    private val firestore:FirebaseFirestore = FirebaseFirestore.getInstance()
    override val repositoryMhs: RepositoryMhs by lazy {
        NetworkRepositoryMhs(firestore)

    }


}
