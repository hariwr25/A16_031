package com.hariwr.uas.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hariwr.uas.ui.view.EditPendapatanScreen
import com.hariwr.uas.ui.view.EditPengeluaranScreen
import com.hariwr.uas.ui.view.HomeScreen
import com.hariwr.uas.ui.view.InputPendapatanScreen
import com.hariwr.uas.ui.view.InputPengeluaranScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        // Rute untuk HomeScreen
        composable("home") { HomeScreen(navController = navController) }

        // Rute untuk InputPendapatanScreen
        composable("inputPendapatan") { InputPendapatanScreen(navController = navController) }

        // Rute untuk InputPengeluaranScreen
        composable("inputPengeluaran") { InputPengeluaranScreen(navController = navController) }

        // Rute untuk EditPendapatanScreen (menggunakan ID pendapatan sebagai parameter)
        composable("editPendapatan/{pendapatanId}") { backStackEntry ->
            EditPendapatanScreen(
                navController = navController,
                pendapatanId = backStackEntry.arguments?.getString("pendapatanId")
            )
        }

        // Rute untuk EditPengeluaranScreen (menggunakan ID pengeluaran sebagai parameter)
        composable("editPengeluaran/{pengeluaranId}") { backStackEntry ->
            EditPengeluaranScreen(
                navController = navController,
                pengeluaranId = backStackEntry.arguments?.getString("pengeluaranId")
            )
        }
    }
}
