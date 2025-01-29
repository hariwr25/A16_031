package com.hariwr.uas.ui.view

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hariwr.uas.model.Aset
import com.hariwr.uas.model.Kategori
import com.hariwr.uas.model.Pendapatan
import com.hariwr.uas.ui.viewmodel.HomeViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPendapatanScreen(
    navController: NavController,
    pendapatanId: String?,
    viewModel: HomeViewModel = viewModel()
) {
    // State untuk input form
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Kategori?>(null) }
    var selectedAsset by remember { mutableStateOf<Aset?>(null) }
    var note by remember { mutableStateOf("") }
    var transactionDate by remember { mutableStateOf("") }

    // Mengambil data kategori dan aset dari viewModel
    val categories = viewModel.kategoriList.observeAsState(listOf()).value
    val assets = viewModel.asetList.observeAsState(listOf()).value

    // Fungsi untuk menangani pemilihan tanggal
    val onDateSelected: (Int, Int, Int) -> Unit = { year, month, dayOfMonth ->
        transactionDate = "$dayOfMonth/${month + 1}/$year"  // Format tanggal (DD/MM/YYYY)
    }

    // Mengatur DatePicker
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        navController.context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            onDateSelected(selectedYear, selectedMonth, selectedDayOfMonth)
        }, year, month, dayOfMonth
    )

    // Memastikan kita mendapatkan data pendapatan yang sesuai berdasarkan ID
    val currentPendapatan =
        viewModel.pendapatanList.observeAsState(listOf()).value.find { it.idPendapatan.toString() == pendapatanId }

    // Jika data pendapatan ditemukan, tampilkan di form
    LaunchedEffect(currentPendapatan) {
        currentPendapatan?.let {
            amount = it.total.toString()
            selectedCategory = categories.find { category -> category.idKategori == it.idKategori }
            selectedAsset = assets.find { asset -> asset.idAset == it.idAset }
            note = it.catatan
            transactionDate = it.tanggalTransaksi
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        // Tombol untuk kembali ke halaman sebelumnya
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Dropdown untuk memilih kategori
        KategoriDropdown(categories, selectedCategory) { selectedCategory = it }

        // Dropdown untuk memilih aset
        AssetDropdown(assets, selectedAsset) { selectedAsset = it }

        // TextField untuk input jumlah pendapatan
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Jumlah Pendapatan") },
            modifier = Modifier.fillMaxWidth()
        )

        // TextField untuk input catatan
        TextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Catatan") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // TextField untuk memilih tanggal transaksi
        TextField(
            value = transactionDate,
            onValueChange = { },
            label = { Text("Tanggal Transaksi") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            readOnly = true // Menjaga agar tidak bisa diedit manual
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol untuk menyimpan perubahan pendapatan
        Button(onClick = {
            // Validasi input untuk memastikan semua kolom terisi
            if (amount.isNotEmpty() && selectedCategory != null && selectedAsset != null && transactionDate.isNotEmpty()) {
                val updatedPendapatan = Pendapatan(
                    idPendapatan = currentPendapatan?.idPendapatan
                        ?: 0,  // Gunakan ID yang ada, atau 0 jika tidak ada
                    idAset = selectedAsset!!.idAset,
                    idKategori = selectedCategory!!.idKategori,
                    tanggalTransaksi = transactionDate,
                    total = amount.toDouble(),
                    catatan = note
                )
                // Memperbarui pendapatan di ViewModel
                viewModel.updatePendapatan(updatedPendapatan)

                // Kembali ke halaman HomeScreen setelah menyimpan data
                navController.popBackStack()
            }
        }) {
            Text("Simpan Perubahan Pendapatan")
        }
    }
}
