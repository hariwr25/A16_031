package com.hariwr.uas.model

data class Pendapatan(
    val idPendapatan: Int,
    val idAset: Int,
    val idKategori: Int,
    val tanggalTransaksi: String,
    val total: Double,
    val catatan: String
)
