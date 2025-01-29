package com.hariwr.uas.repository

import com.hariwr.uas.model.Aset
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

    // Mengambil daftar aset dari API
    suspend fun getAset(): List<Aset> {
        val response = apiService.getAset()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error fetching assets")
        }
    }

    // Menambahkan aset baru melalui API
    suspend fun tambahAset(aset: Aset) {
        val response = apiService.tambahAset(aset)
        if (!response.isSuccessful) {
            throw Exception("Failed to add asset")
        }
    }

    // Memperbarui aset yang ada melalui API
    suspend fun updateAset(aset: Aset) {
        val response = apiService.updateAset(aset)
        if (!response.isSuccessful) {
            throw Exception("Failed to update asset")
        }
    }

    // Menghapus aset melalui API
    suspend fun deleteAset(aset: Aset) {
        val response = apiService.deleteAset(aset)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete asset")
        }
    }
}