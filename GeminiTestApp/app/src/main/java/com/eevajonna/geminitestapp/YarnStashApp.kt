package com.eevajonna.geminitestapp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eevajonna.geminitestapp.data.NeedleType
import com.eevajonna.geminitestapp.ui.HomeScreen
import com.eevajonna.geminitestapp.ui.NeedleAddScreen
import com.eevajonna.geminitestapp.ui.NeedleDetailsScreen
import com.eevajonna.geminitestapp.ui.NeedleListScreen
import com.eevajonna.geminitestapp.ui.YarnAddScreen
import com.eevajonna.geminitestapp.ui.YarnDetailsScreen
import com.eevajonna.geminitestapp.ui.YarnListScreen
import com.eevajonna.geminitestapp.viewmodel.NeedleViewModel
import com.eevajonna.geminitestapp.viewmodel.YarnViewModel

enum class Screen {
    Home, YarnList, YarnDetails, YarnAdd, NeedleList, NeedleDetails,
    NeedleAdd
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnStashApp() {
    val navController = rememberNavController()
    val yarnViewModel: YarnViewModel = viewModel()
    val needleViewModel: NeedleViewModel = viewModel()
    // Add example data:
    addExampleData(yarnViewModel, needleViewModel)

    NavHost(navController = navController, startDestination = Screen.Home.name) {
        composable(Screen.Home.name) {
            HomeScreen(navController)
        }
        composable(Screen.YarnList.name) {
            YarnListScreen(navController, yarnViewModel)
        }
        composable(Screen.YarnDetails.name) {
            YarnDetailsScreen(navController, yarnViewModel)
        }
        composable(Screen.YarnAdd.name) {
            YarnAddScreen(navController, yarnViewModel)
        }
        composable(Screen.NeedleList.name) {
            NeedleListScreen(navController, needleViewModel)
        }
        composable(Screen.NeedleDetails.name) {
            NeedleDetailsScreen(navController, needleViewModel)
        }
        composable(Screen.NeedleAdd.name) {
            NeedleAddScreen(navController, needleViewModel)
        }
    }
}

fun addExampleData(yarnViewModel: YarnViewModel, needleViewModel: NeedleViewModel) {
    //add yarns
    yarnViewModel.addYarn(
        brand = "Drops",
        name = "Nepal",
        colorway = "Light Grey",
        meterage = 75,
        skeinWeight = 50,
        skeins = 3,
        picture = null
    )
    yarnViewModel.addYarn(
        brand = "Katia",
        name = "Merino Sport",
        colorway = "Light Pink",
        meterage = 80,
        skeinWeight = 50,
        skeins = 2,
        picture = null
    )
    yarnViewModel.addYarn(
        brand = "Rico Design",
        name = "Essentials Soft Merino Aran",
        colorway = "Turquoise",
        meterage = 100,
        skeinWeight = 50,
        skeins = 5,
        picture = null
    )
    //add needles
    needleViewModel.addNeedle(
        brand = "Addi",
        size = 4.5,
        type = NeedleType.Circular
    )
    needleViewModel.addNeedle(
        brand = "KnitPro",
        size = 3.5,
        type = NeedleType.SinglePointed
    )
    needleViewModel.addNeedle(
        brand = "Chiaogoo",
        size = 6.0,
        type = NeedleType.DoublePointed
    )
}