package com.hariwr.uas.repository

import com.hariwr.uas.model.Aset
import com.hariwr.uas.model.Kategori
import com.hariwr.uas.model.Pendapatan
import com.hariwr.uas.model.Pengeluaran
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

    // Mengambil daftar pendapatan dari API
    suspend fun getPendapatan(): List<Pendapatan> {
        val response = apiService.getPendapatan()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error fetching pendapatan")
        }
    }

    // Menambahkan data pendapatan baru melalui API
    suspend fun tambahPendapatan(pendapatan: Pendapatan) {
        val response = apiService.tambahPendapatan(pendapatan)
        if (!response.isSuccessful) {
            throw Exception("Failed to add pendapatan")
        }
    }

    // Memperbarui data pendapatan yang ada melalui API
    suspend fun updatePendapatan(pendapatan: Pendapatan) {
        val response = apiService.updatePendapatan(pendapatan)
        if (!response.isSuccessful) {
            throw Exception("Failed to update pendapatan")
        }
    }

    // Menghapus data pendapatan melalui API
    suspend fun deletePendapatan(pendapatan: Pendapatan) {
        val response = apiService.deletePendapatan(pendapatan)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete pendapatan")
        }
    }

    // Mengambil daftar pengeluaran dari API
    suspend fun getPengeluaran(): List<Pengeluaran> {
        val response = apiService.getPengeluaran()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error fetching pengeluaran")
        }
    }

    // Menambahkan data pengeluaran baru melalui API
    suspend fun tambahPengeluaran(pengeluaran: Pengeluaran) {
        val response = apiService.tambahPengeluaran(pengeluaran)
        if (!response.isSuccessful) {
            throw Exception("Failed to add pengeluaran")
        }
    }

    // Memperbarui data pengeluaran yang ada melalui API
    suspend fun updatePengeluaran(pengeluaran: Pengeluaran) {
        val response = apiService.updatePengeluaran(pengeluaran)
        if (!response.isSuccessful) {
            throw Exception("Failed to update pengeluaran")
        }
    }

    // Menghapus data pengeluaran melalui API
    suspend fun deletePengeluaran(pengeluaran: Pengeluaran) {
        val response = apiService.deletePengeluaran(pengeluaran)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete pengeluaran")
        }
    }
}
