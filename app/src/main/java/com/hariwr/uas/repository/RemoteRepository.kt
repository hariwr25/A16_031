package com.hariwr.uas.repository

import com.hariwr.uas.model.Kategori
import com.hariwr.uas.network.ApiService

class RemoteRepository(private val apiService: ApiService) {
    // Mengambil daftar kategori dari API
    suspend fun getKategori(): List<Kategori> {
        val response = apiService.getKategori()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error fetching categories")
        }
    }

    // Menambahkan kategori baru melalui API
    suspend fun tambahKategori(kategori: Kategori) {
        val response = apiService.tambahKategori(kategori)
        if (!response.isSuccessful) {
            throw Exception("Failed to add category")
        }
    }

    // Memperbarui kategori yang ada melalui API
    suspend fun updateKategori(kategori: Kategori) {
        val response = apiService.updateKategori(kategori)
        if (!response.isSuccessful) {
            throw Exception("Failed to update category")
        }
    }

    // Menghapus kategori melalui API
    suspend fun deleteKategori(kategori: Kategori) {
        val response = apiService.deleteKategori(kategori)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete category")
        }
    }
}