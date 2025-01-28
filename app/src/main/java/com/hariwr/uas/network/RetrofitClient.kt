package com.hariwr.uas.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Menggunakan IP 10.0.0.2 untuk mengakses localhost dari emulator
    private const val BASE_URL = "http://10.0.2.2/finance_manager_api/"

    // Retrofit instance untuk melakukan permintaan API
    private
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)  // Base URL dengan 10.0.0.2
        .addConverterFactory(GsonConverterFactory.create())  // Menggunakan Gson untuk mengonversi JSON
        .build()

    // Fungsi untuk mendapatkan instance ApiService
    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
