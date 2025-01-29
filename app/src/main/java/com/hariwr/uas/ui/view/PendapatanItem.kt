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
import com.hariwr.uas.model.Pendapatan
import com.hariwr.uas.ui.viewmodel.HomeViewModel

@Composable
fun PendapatanItem(
    pendapatan: Pendapatan,
    onEdit: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    // Fetching the category and asset by ID from the view model
    val category =
        viewModel.kategoriList.observeAsState().value?.find { it.idKategori == pendapatan.idKategori }
    val asset = viewModel.asetList.observeAsState().value?.find { it.idAset == pendapatan.idAset }

    // Card for displaying the pendapatan item
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
                text = "Pendapatan: Rp. ${pendapatan.total}",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Kategori: ${category?.namaKategori ?: "Unknown"}")
            Text(text = "Aset: ${asset?.namaAset ?: "Unknown"}")
            Text(text = "Tanggal: ${pendapatan.tanggalTransaksi}")
            Text(text = "Catatan: ${pendapatan.catatan}")
            Spacer(modifier = Modifier.height(8.dp))

            // Edit button for pendapatan
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
