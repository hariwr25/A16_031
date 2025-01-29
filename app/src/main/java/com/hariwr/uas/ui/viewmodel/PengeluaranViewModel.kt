package com.hariwr.uas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariwr.uas.model.Pengeluaran
import com.hariwr.uas.repository.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PengeluaranViewModel(private val repository: RemoteRepository) : ViewModel() {

    private val _pengeluaran = MutableStateFlow<List<Pengeluaran>>(emptyList())
    val pengeluaran: StateFlow<List<Pengeluaran>> = _pengeluaran

    init {
        getPengeluaran()
    }

    fun getPengeluaran() {
        viewModelScope.launch {
            try {
                _pengeluaran.value = repository.getPengeluaran()
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun addPengeluaran(pengeluaran: Pengeluaran) {
        viewModelScope.launch {
            try {
                repository.tambahPengeluaran(pengeluaran)
                getPengeluaran() // Refresh the pengeluaran list after adding
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun updatePengeluaran(pengeluaran: Pengeluaran) {
        viewModelScope.launch {
            try {
                repository.updatePengeluaran(pengeluaran)
                getPengeluaran() // Refresh the pengeluaran list after updating
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun deletePengeluaran(pengeluaran: Pengeluaran) {
        viewModelScope.launch {
            try {
                repository.deletePengeluaran(pengeluaran)
                getPengeluaran() // Refresh the pengeluaran list after deleting
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }
}
