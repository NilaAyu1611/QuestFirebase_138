package com.example.pam_firestore

import android.app.Application
import com.example.pam_firestore.di.MahasiswaContainer

class MahasiswaApp : Application() {
    // fungsinya untuk menyimpan instance containerapp
    lateinit var containerApp: MahasiswaContainer

    override fun onCreate() {
        super.onCreate()
        // membuat instance containerapp
        containerApp = MahasiswaContainer(this)
        // instance adalah yang dibuat dari class
    }
}