package com.hariwr.uas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariwr.uas.model.Aset
import com.hariwr.uas.model.Kategori
import com.hariwr.uas.model.Pendapatan
import com.hariwr.uas.model.Pengeluaran
import com.hariwr.uas.network.ApiService
import com.hariwr.uas.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitClient.getApiService()

    // LiveData untuk saldo dan lainnya
    private val _saldo = MutableLiveData<Double>()
    val saldo: LiveData<Double> get() = _saldo

    private val _totalPengeluaran = MutableLiveData<Double>()
    val totalPengeluaran: LiveData<Double> get() = _totalPengeluaran

    private val _totalPendapatan = MutableLiveData<Double>()
    val totalPendapatan: LiveData<Double> get() = _totalPendapatan

    // MutableLiveData dengan default non-nullable list (emptyList)
    private val _pengeluaranList = MutableLiveData<List<Pengeluaran>>(emptyList())
    val pengeluaranList: LiveData<List<Pengeluaran>> get() = _pengeluaranList

    // MutableLiveData dengan default non-nullable list (emptyList)
    private val _pendapatanList = MutableLiveData<List<Pendapatan>>(emptyList())
    val pendapatanList: LiveData<List<Pendapatan>> get() = _pendapatanList

    private val _kategoriList = MutableLiveData<List<Kategori>>()
    val kategoriList: LiveData<List<Kategori>> get() = _kategoriList

    private val _asetList = MutableLiveData<List<Aset>>()
    val asetList: LiveData<List<Aset>> get() = _asetList

    init {
        // Ambil data dari API atau sumber lokal saat ViewModel pertama kali dimuat
        getPengeluaran()
        getPendapatan()
        getKategori()
        getAset()
    }

    // Ambil data pengeluaran
    fun getPengeluaran() {
        viewModelScope.launch {
            try {
                val response = apiService.getPengeluaran()
                if (response.isSuccessful) {
                    val pengeluaranList = response.body() ?: emptyList()
                    _pengeluaranList.postValue(pengeluaranList) // Pastikan tidak null
                    calculateTotalPengeluaran(pengeluaranList)
                } else {
                    Log.e("API Error", "Failed to fetch Pengeluaran data")
                }
            } catch (e: Exception) {
                Log.e("API Exception", "Error: ${e.message}")
            }
        }
    }

    // Ambil data pendapatan
    fun getPendapatan() {
        viewModelScope.launch {
            try {
                val response = apiService.getPendapatan()
                if (response.isSuccessful) {
                    val pendapatanList = response.body() ?: emptyList()
                    _pendapatanList.postValue(pendapatanList) // Pastikan tidak null
                    calculateTotalPendapatan(pendapatanList)
                } else {
                    Log.e("API Error", "Failed to fetch Pendapatan data")
                }
            } catch (e: Exception) {
                Log.e("API Exception", "Error: ${e.message}")
            }
        }
    }

    // Ambil data kategori
    fun getKategori() {
        viewModelScope.launch {
            try {
                val response = apiService.getKategori()
                if (response.isSuccessful) {
                    _kategoriList.postValue(response.body() ?: emptyList())
                } else {
                    Log.e("API Error", "Failed to fetch Kategori data")
                }
            } catch (e: Exception) {
                Log.e("API Exception", "Error: ${e.message}")
            }
        }
    }

    // Ambil data aset
    fun getAset() {
        viewModelScope.launch {
            try {
                val response = apiService.getAset()
                if (response.isSuccessful) {
                    _asetList.postValue(response.body() ?: emptyList())
                } else {
                    Log.e("API Error", "Failed to fetch Aset data")
                }
            } catch (e: Exception) {
                Log.e("API Exception", "Error: ${e.message}")
            }
        }
    }

    // Fungsi untuk menambahkan pendapatan
    fun addPendapatan(pendapatan: Pendapatan) {
        viewModelScope.launch {
            val currentList = _pendapatanList.value.orEmpty() // Handle null list
            _pendapatanList.postValue(currentList + pendapatan)
            calculateTotalPendapatan(_pendapatanList.value)
        }
    }

    // Fungsi untuk menambahkan pengeluaran
    fun addPengeluaran(pengeluaran: Pengeluaran) {
        viewModelScope.launch {
            val currentList = _pengeluaranList.value.orEmpty() // Handle null list
            _pengeluaranList.postValue(currentList + pengeluaran)
            calculateTotalPengeluaran(_pengeluaranList.value)
        }
    }

    // Fungsi untuk mengupdate pendapatan
    fun updatePendapatan(pendapatan: Pendapatan) {
        viewModelScope.launch {
            val updatedList = _pendapatanList.value?.map {
                if (it.idPendapatan == pendapatan.idPendapatan) {
                    pendapatan
                } else {
                    it
                }
            } ?: emptyList()
            _pendapatanList.postValue(updatedList)
            calculateTotalPendapatan(updatedList)
        }
    }

    // Fungsi untuk mengupdate pengeluaran
    fun updatePengeluaran(pengeluaran: Pengeluaran) {
        viewModelScope.launch {
            val updatedList = _pengeluaranList.value?.map {
                if (it.idPengeluaran == pengeluaran.idPengeluaran) {
                    pengeluaran
                } else {
                    it
                }
            } ?: emptyList()
            _pengeluaranList.postValue(updatedList)
            calculateTotalPengeluaran(updatedList)
        }
    }

    // Menghitung total pengeluaran
    private fun calculateTotalPengeluaran(pengeluaranList: List<Pengeluaran>?) {
        val total = pengeluaranList?.sumOf { it.total } ?: 0.0
        _totalPengeluaran.postValue(total)
        updateSaldo(totalPendapatan.value ?: 0.0, total)
    }

    // Menghitung total pendapatan
    private fun calculateTotalPendapatan(pendapatanList: List<Pendapatan>?) {
        val total = pendapatanList?.sumOf { it.total } ?: 0.0
        _totalPendapatan.postValue(total)
        updateSaldo(total, totalPengeluaran.value ?: 0.0)
    }

    // Menghitung saldo
    private fun updateSaldo(pendapatan: Double, pengeluaran: Double) {
        val saldo = pendapatan - pengeluaran
        _saldo.postValue(saldo)
    }
}
