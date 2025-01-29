package com.hariwr.uas.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    // State untuk kontrol dialog
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header dengan saldo dan total
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant // Background color for card
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Saldo: Rp. ${saldo}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = saldoColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Pendapatan: Rp. $totalPendapatan")
                Text("Total Pengeluaran: Rp. $totalPengeluaran")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button untuk menambah pendapatan
        Button(
            onClick = { navController.navigate("inputPendapatan") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)) // Custom button color
        ) {
            Text("Tambah Pendapatan", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button untuk menambah pengeluaran
        Button(
            onClick = {
                if (saldo < 0) {
                    showDialog = true
                } else {
                    navController.navigate("inputPengeluaran")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)) // Custom button color
        ) {
            Text("Tambah Pengeluaran", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menampilkan semua pendapatan
        Text("Pendapatan:", style = MaterialTheme.typography.bodyLarge)
        LazyColumn {
            items(pendapatanList) { item ->
                PendapatanItem(pendapatan = item, onEdit = {
                    navController.navigate("editPendapatan/{${item.idPendapatan}}")
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menampilkan semua pengeluaran
        Text("Pengeluaran:", style = MaterialTheme.typography.bodyLarge)
        LazyColumn {
            items(pengeluaranList) { item ->
                PengeluaranItem(pengeluaran = item, onEdit = {
                    navController.navigate("editPengeluaran/{${item.idPengeluaran}}")
                })
            }
        }
    }

    // Menampilkan dialog jika showDialog bernilai true
    if (showDialog) {
        showDialogNegativeBalance(navController) { showDialog = false }
    }
}

@Composable
fun showDialogNegativeBalance(navController: NavController, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Perhatian") },
        text = { Text("Keuangan Anda sudah minus. Apakah Anda yakin menambah pengeluaran?") },
        confirmButton = {
            TextButton(onClick = {
                navController.navigate("inputPengeluaran")
                onDismiss() // Menutup dialog setelah navigasi
            }) {
                Text("Ya")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Tidak")
            }
        }
    )
}
