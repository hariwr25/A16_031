package com.hariwr.uas.repository

import com.hariwr.uas.model.Pendapatan
import com.hariwr.uas.model.Pengeluaran
import com.hariwr.uas.network.ApiService

class NetworkRepository(private val apiService: ApiService) {
    // Fungsi untuk menangani respons jaringan secara umum
    private suspend fun <T> makeRequest(call: suspend () -> retrofit2.Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Throwable("Failed to load data: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e) // Mengembalikan error jika terjadi exception
        }
    }
    // Mengambil data Pengeluaran dari API
    suspend fun getPengeluaran(): Result<List<Pengeluaran>> {
        return makeRequest { apiService.getPengeluaran() }
    }
    // Mengambil data Pendapatan dari API
    suspend fun getPendapatan(): Result<List<Pendapatan>> {
        return makeRequest { apiService.getPendapatan() }
    }
}

