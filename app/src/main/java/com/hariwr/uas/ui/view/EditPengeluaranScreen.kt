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
import com.hariwr.uas.model.Pengeluaran
import com.hariwr.uas.ui.viewmodel.HomeViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPengeluaranScreen(
    navController: NavController,
    pengeluaranId: String?,
    viewModel: HomeViewModel = viewModel()
) {
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Kategori?>(null) }
    var selectedAsset by remember { mutableStateOf<Aset?>(null) }
    var note by remember { mutableStateOf("") }
    var transactionDate by remember { mutableStateOf("") }

    val categories = viewModel.kategoriList.observeAsState(listOf()).value
    val assets = viewModel.asetList.observeAsState(listOf()).value

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        navController.context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            transactionDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        },
        year,
        month,
        dayOfMonth
    )

    val currentPengeluaran =
        viewModel.pengeluaranList.observeAsState(listOf()).value.find { it.idPengeluaran.toString() == pengeluaranId }

    LaunchedEffect(currentPengeluaran) {
        currentPengeluaran?.let {
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
        // Back Button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Category Dropdown
        KategoriDropdown(categories, selectedCategory) { selectedCategory = it }

        // Asset Dropdown
        AssetDropdown(assets, selectedAsset) { selectedAsset = it }

        // Amount TextField
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Jumlah Pengeluaran") },
            modifier = Modifier.fillMaxWidth()
        )

        // Note TextField
        TextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Catatan") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Transaction Date Picker
        TextField(
            value = transactionDate,
            onValueChange = {},
            label = { Text("Tanggal Transaksi") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            readOnly = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(onClick = {
            if (amount.isNotEmpty() && selectedCategory != null && selectedAsset != null && transactionDate.isNotEmpty()) {
                val updatedPengeluaran = Pengeluaran(
                    idPengeluaran = currentPengeluaran?.idPengeluaran ?: 0,
                    idAset = selectedAsset!!.idAset,
                    idKategori = selectedCategory!!.idKategori,
                    tanggalTransaksi = transactionDate,
                    total = amount.toDouble(),
                    catatan = note
                )
                viewModel.updatePengeluaran(updatedPengeluaran)
                navController.popBackStack()
            }
        }) {
            Text("Simpan Perubahan Pengeluaran")
        }
    }
}
