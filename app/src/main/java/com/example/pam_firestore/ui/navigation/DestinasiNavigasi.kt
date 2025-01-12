package com.example.pam_firestore.ui.navigation

interface DestinasiNavigasi {       // untuk menyimpan riuting halaman
    val route: String
    val titleRes: String
}

object DestinasiHome : DestinasiNavigasi{
    override val route: String = "home"
    override val titleRes: String = "Home"
}
