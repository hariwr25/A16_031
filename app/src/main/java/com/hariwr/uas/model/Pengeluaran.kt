package com.hariwr.uas.model

data class Pengeluaran(
    val idPengeluaran: Int,
    val idAset: Int,
    val idKategori: Int,
    val tanggalTransaksi: String,
    val total: Double,
    val catatan: String
)
