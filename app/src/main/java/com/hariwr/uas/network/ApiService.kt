package com.hariwr.uas.network

import com.hariwr.uas.model.Aset
import com.hariwr.uas.model.Kategori
import com.hariwr.uas.model.Pendapatan
import com.hariwr.uas.model.Pengeluaran
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    // Kategori
    @GET("kategori/tampil_kategori.php")
    suspend fun getKategori(): Response<List<Kategori>>

    @POST("kategori/tambah_kategori.php")
    suspend fun tambahKategori(@Body kategori: Kategori): Response<ApiResponse>

    @PUT("kategori/update_kategori.php")
    suspend fun updateKategori(@Body kategori: Kategori): Response<ApiResponse>

    @DELETE("kategori/delete_kategori.php")
    suspend fun deleteKategori(@Body kategori: Kategori): Response<ApiResponse>


    // Aset
    @GET("aset/tampil_aset.php")
    suspend fun getAset(): Response<List<Aset>>

    @POST("aset/tambah_aset.php")
    suspend fun tambahAset(@Body aset: Aset): Response<ApiResponse>

    @PUT("aset/update_aset.php")
    suspend fun updateAset(@Body aset: Aset): Response<ApiResponse>

    @DELETE("aset/delete_aset.php")
    suspend fun deleteAset(@Body aset: Aset): Response<ApiResponse>


    // Pendapatan
    @GET("pendapatan/tampil_pendapatan.php")
    suspend fun getPendapatan(): Response<List<Pendapatan>>

    @POST("pendapatan/tambah_pendapatan.php")
    suspend fun tambahPendapatan(@Body pendapatan: Pendapatan): Response<ApiResponse>

    @PUT("pendapatan/update_pendapatan.php")
    suspend fun updatePendapatan(@Body pendapatan: Pendapatan): Response<ApiResponse>

    @DELETE("pendapatan/delete_pendapatan.php")
    suspend fun deletePendapatan(@Body pendapatan: Pendapatan): Response<ApiResponse>


    // Pengeluaran
    @GET("pengeluaran/tampil_pengeluaran.php")
    suspend fun getPengeluaran(): Response<List<Pengeluaran>>

    @POST("pengeluaran/tambah_pengeluaran.php")
    suspend fun tambahPengeluaran(@Body pengeluaran: Pengeluaran): Response<ApiResponse>

    @PUT("pengeluaran/update_pengeluaran.php")
    suspend fun updatePengeluaran(@Body pengeluaran: Pengeluaran): Response<ApiResponse>

    @DELETE("pengeluaran/delete_pengeluaran.php")
    suspend fun deletePengeluaran(@Body pengeluaran: Pengeluaran): Response<ApiResponse>
}
