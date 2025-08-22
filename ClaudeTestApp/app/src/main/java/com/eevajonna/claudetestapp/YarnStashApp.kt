package com.eevajonna.claudetestapp

import androidx.compose.foundation.layout.add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eevajonna.claudetestapp.data.model.KnittingNeedle
import com.eevajonna.claudetestapp.data.model.NeedleType
import com.eevajonna.claudetestapp.data.model.Yarn
import com.eevajonna.claudetestapp.ui.screens.AddNeedleScreen
import com.eevajonna.claudetestapp.ui.screens.AddYarnScreen
import com.eevajonna.claudetestapp.ui.screens.HomeScreen
import com.eevajonna.claudetestapp.ui.screens.NeedleDetailsScreen
import com.eevajonna.claudetestapp.ui.screens.NeedleListScreen
import com.eevajonna.claudetestapp.ui.screens.SearchScreen
import com.eevajonna.claudetestapp.ui.screens.SettingsScreen
import com.eevajonna.claudetestapp.ui.screens.YarnDetailsScreen
import com.eevajonna.claudetestapp.ui.screens.YarnListScreen

@Composable
fun YarnStashApp() {
    val navController = rememberNavController()

    // Sample data - in a real app, this would come from a ViewModel/Repository
    val sampleYarns = remember {
        mutableStateListOf(
            Yarn("1", "Lion Brand", "Wool-Ease", "Forest Green Heather", 197, 85, 3),
            Yarn("2", "Red Heart", "Super Saver", "Royal Blue", 364, 198, 2),
            Yarn("3", "Bernat", "Baby", "Soft Pink", 120, 50, 5)
        )
    }

    val sampleNeedles = remember {
        mutableStateListOf(
            KnittingNeedle("1", "ChiaoGoo", 4.0, NeedleType.CIRCULAR),
            KnittingNeedle("2", "Addi", 3.5, NeedleType.DOUBLE_POINTED),
            KnittingNeedle("3", "Clover", 5.0, NeedleType.SINGLE_POINTED),
            KnittingNeedle("4", "HiyaHiya", 2.75, NeedleType.CIRCULAR)
        )
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                yarnCount = sampleYarns.size,
                needleCount = sampleNeedles.size,
                totalSkeins = sampleYarns.sumOf { it.skeinCount }
            )
        }
        composable("yarn_list") {
            YarnListScreen(
                navController = navController,
                yarns = sampleYarns
            )
        }
        composable("yarn_details/{yarnId}") { backStackEntry ->
            val yarnId = backStackEntry.arguments?.getString("yarnId")
            val yarn = sampleYarns.find { it.id == yarnId }
            yarn?.let {
                YarnDetailsScreen(
                    navController = navController,
                    yarn = it
                )
            }
        }
        composable("needle_list") {
            NeedleListScreen(
                navController = navController,
                needles = sampleNeedles
            )
        }
        composable("needle_details/{needleId}") { backStackEntry ->
            val needleId = backStackEntry.arguments?.getString("needleId")
            val needle = sampleNeedles.find { it.id == needleId }
            needle?.let {
                NeedleDetailsScreen(
                    navController = navController,
                    needle = it
                )
            }
        }
        composable("add_yarn") {
            AddYarnScreen(
                navController = navController,
                onYarnAdded = { yarn ->
                    sampleYarns.add(yarn)
                }
            )
        }
        composable("add_needle") {
            AddNeedleScreen(
                navController = navController,
                onNeedleAdded = { needle ->
                    sampleNeedles.add(needle)
                }
            )
        }
        composable("search") {
            SearchScreen(
                navController = navController,
                yarns = sampleYarns,
                needles = sampleNeedles
            )
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
    }
}