package com.hariwr.uas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariwr.uas.model.Aset
import com.hariwr.uas.repository.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AsetViewModel(private val repository: RemoteRepository) : ViewModel() {

    private val _aset = MutableStateFlow<List<Aset>>(emptyList())
    val aset: StateFlow<List<Aset>> = _aset

    init {
        getAset()
    }

    fun getAset() {
        viewModelScope.launch {
            try {
                _aset.value = repository.getAset()
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun addAset(aset: Aset) {
        viewModelScope.launch {
            try {
                repository.tambahAset(aset)
                getAset() // Refresh the aset list after adding
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun updateAset(aset: Aset) {
        viewModelScope.launch {
            try {
                repository.updateAset(aset)
                getAset() // Refresh the aset list after updating
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }

    fun deleteAset(aset: Aset) {
        viewModelScope.launch {
            try {
                repository.deleteAset(aset)
                getAset() // Refresh the aset list after deleting
            } catch (e: Exception) {
                // Handle error (could show error message to user)
            }
        }
    }
}
