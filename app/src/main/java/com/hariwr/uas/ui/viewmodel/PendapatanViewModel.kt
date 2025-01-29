package com.hariwr.uas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariwr.uas.model.Pendapatan
import com.hariwr.uas.repository.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PendapatanViewModel(private val repository: RemoteRepository) : ViewModel() {

    private val _pendapatan = MutableStateFlow<List<Pendapatan>>(emptyList())
    val pendapatan: StateFlow<List<Pendapatan>> = _pendapatan

    init {
        getPendapatan()
    }

    fun getPendapatan() {
        viewModelScope.launch {
            try {
                _pendapatan.value = repository.getPendapatan()
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun addPendapatan(pendapatan: Pendapatan) {
        viewModelScope.launch {
            try {
                repository.tambahPendapatan(pendapatan)
                getPendapatan() // Refresh the pendapatan list after adding
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun updatePendapatan(pendapatan: Pendapatan) {
        viewModelScope.launch {
            try {
                repository.updatePendapatan(pendapatan)
                getPendapatan() // Refresh the pendapatan list after updating
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun deletePendapatan(pendapatan: Pendapatan) {
        viewModelScope.launch {
            try {
                repository.deletePendapatan(pendapatan)
                getPendapatan() // Refresh the pendapatan list after deleting
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }
}
