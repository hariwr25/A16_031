package com.hariwr.uas.ui.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hariwr.uas.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(), navController: NavController) {
// Mengambil data dari LiveData
    val saldo by viewModel.saldo.observeAsState(0.0)
    val totalPendapatan by viewModel.totalPendapatan.observeAsState(0.0)
    val totalPengeluaran by viewModel.totalPengeluaran.observeAsState(0.0)

    // Mengatur warna saldo (hijau jika saldo positif, merah jika saldo negatif)
    val saldoColor =
        if (saldo >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    // Mengambil daftar pengeluaran dan pendapatan
    val pengeluaranList = viewModel.pengeluaranList.observeAsState(listOf()).value
    val pendapatanList = viewModel.pendapatanList.observeAsState(listOf()).value

}
