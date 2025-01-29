package com.hariwr.uas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariwr.uas.model.Kategori
import com.hariwr.uas.repository.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KategoriViewModel(private val repository: RemoteRepository) : ViewModel() {

    private val _kategori = MutableStateFlow<List<Kategori>>(emptyList())
    val kategori: StateFlow<List<Kategori>> = _kategori

    init {
        getKategori()
    }

    fun getKategori() {
        viewModelScope.launch {
            try {
                _kategori.value = repository.getKategori()
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun addKategori(kategori: Kategori) {
        viewModelScope.launch {
            try {
                repository.tambahKategori(kategori)
                getKategori() // Refresh the kategori list after adding
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun updateKategori(kategori: Kategori) {
        viewModelScope.launch {
            try {
                repository.updateKategori(kategori)
                getKategori() // Refresh the kategori list after updating
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun deleteKategori(kategori: Kategori) {
        viewModelScope.launch {
            try {
                repository.deleteKategori(kategori)
                getKategori() // Refresh the kategori list after deleting
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }
}
