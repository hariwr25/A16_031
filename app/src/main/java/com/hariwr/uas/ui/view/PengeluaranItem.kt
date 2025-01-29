package com.hariwr.uas.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hariwr.uas.model.Pengeluaran
import com.hariwr.uas.ui.viewmodel.HomeViewModel

@Composable
fun PengeluaranItem(
    pengeluaran: Pengeluaran,
    onEdit: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    // Observing kategoriList and asetList from the ViewModel
    val category =
        viewModel.kategoriList.observeAsState().value?.find { it.idKategori == pengeluaran.idKategori }
    val asset = viewModel.asetList.observeAsState().value?.find { it.idAset == pengeluaran.idAset }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Pengeluaran: Rp. ${pengeluaran.total}",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Kategori: ${category?.namaKategori ?: "Unknown"}")
            Text(text = "Aset: ${asset?.namaAset ?: "Unknown"}")
            Text(text = "Tanggal: ${pengeluaran.tanggalTransaksi}")
            Text(text = "Catatan: ${pengeluaran.catatan}")
            Spacer(modifier = Modifier.height(8.dp))

            // Button for editing pengeluaran
            Text(
                text = "Edit",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onEdit() }
                    .padding(8.dp)
            )
        }
    }
}
