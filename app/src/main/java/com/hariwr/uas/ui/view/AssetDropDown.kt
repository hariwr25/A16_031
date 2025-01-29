package com.hariwr.uas.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hariwr.uas.model.Aset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDropdown(
    assets: List<Aset>,
    selectedAsset: Aset?,
    onAssetSelected: (Aset) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Debug log to check if the assets list is being passed correctly
    println("Assets List: $assets")

    // ExposedDropdownMenuBox is used for better user experience and behavior consistency
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded } // Toggling the dropdown visibility
    ) {
        // TextField to show the selected asset or the placeholder text
        Text(
            text = selectedAsset?.namaAset
                ?: "Pilih Aset", // Display selected asset or a default text
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded // Toggle the dropdown
                    println("Dropdown Expanded: $expanded") // Debug log to check if dropdown expands
                }
                .padding(16.dp)
        )

        // ExposedDropdownMenu to display the list of assets
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false } // Close the dropdown when dismissed
        ) {
            // Loop through assets and create DropdownMenuItem for each asset
            assets.forEach { asset ->
                DropdownMenuItem(onClick = {
                    onAssetSelected(asset) // Update the selected asset
                    expanded = false // Close the dropdown
                    println("Asset Selected: ${asset.namaAset}") // Debug log to see which asset was selected
                }) {
                    Text(asset.namaAset) // Display the name of the asset
                }
            }
        }
    }
}
