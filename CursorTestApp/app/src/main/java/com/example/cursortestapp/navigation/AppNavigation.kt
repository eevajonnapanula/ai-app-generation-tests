package com.example.cursortestapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cursortestapp.ui.screens.needles.NeedlesDetailScreen
import com.example.cursortestapp.ui.screens.needles.NeedlesEditScreen
import com.example.cursortestapp.ui.screens.needles.NeedlesListScreen
import com.example.cursortestapp.ui.screens.yarn.YarnDetailScreen
import com.example.cursortestapp.ui.screens.yarn.YarnEditScreen
import com.example.cursortestapp.ui.screens.yarn.YarnListScreen

sealed class Screen(val route: String) {
    object YarnList : Screen("yarn_list")
    object YarnDetail : Screen("yarn_detail/{brand}/{name}/{colorway}/{yardage}/{weight}/{skeins}") {
        fun createRoute(brand: String, name: String, colorway: String, yardage: Int, weight: Int, skeins: Int) =
            "yarn_detail/$brand/$name/$colorway/$yardage/$weight/$skeins"
    }
    object YarnEdit : Screen("yarn_edit/{brand}/{name}/{colorway}/{yardage}/{weight}/{skeins}") {
        fun createRoute(brand: String, name: String, colorway: String, yardage: Int, weight: Int, skeins: Int) =
            "yarn_edit/$brand/$name/$colorway/$yardage/$weight/$skeins"
    }
    object NeedlesList : Screen("needles_list")
    object NeedlesDetail : Screen("needles_detail/{brand}/{size}/{type}/{length}/{material}") {
        fun createRoute(brand: String, size: String, type: String, length: Int, material: String) =
            "needles_detail/$brand/$size/$type/$length/$material"
    }
    object NeedlesEdit : Screen("needles_edit/{brand}/{size}/{type}/{length}/{material}") {
        fun createRoute(brand: String, size: String, type: String, length: Int, material: String) =
            "needles_edit/$brand/$size/$type/$length/$material"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.YarnList.route
    ) {
        composable(Screen.YarnList.route) {
            YarnListScreen(navController)
        }

        composable(
            route = Screen.YarnDetail.route,
            arguments = listOf(
                navArgument("brand") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("colorway") { type = NavType.StringType },
                navArgument("yardage") { type = NavType.IntType },
                navArgument("weight") { type = NavType.IntType },
                navArgument("skeins") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val brand = backStackEntry.arguments?.getString("brand") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val colorway = backStackEntry.arguments?.getString("colorway") ?: ""
            val yardage = backStackEntry.arguments?.getInt("yardage") ?: 0
            val weight = backStackEntry.arguments?.getInt("weight") ?: 0
            val skeins = backStackEntry.arguments?.getInt("skeins") ?: 0

            YarnDetailScreen(
                navController = navController,
                brand = brand,
                name = name,
                colorway = colorway,
                yardage = yardage,
                weight = weight,
                skeins = skeins
            )
        }

        composable(
            route = Screen.YarnEdit.route,
            arguments = listOf(
                navArgument("brand") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("colorway") { type = NavType.StringType },
                navArgument("yardage") { type = NavType.IntType },
                navArgument("weight") { type = NavType.IntType },
                navArgument("skeins") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val brand = backStackEntry.arguments?.getString("brand") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val colorway = backStackEntry.arguments?.getString("colorway") ?: ""
            val yardage = backStackEntry.arguments?.getInt("yardage") ?: 0
            val weight = backStackEntry.arguments?.getInt("weight") ?: 0
            val skeins = backStackEntry.arguments?.getInt("skeins") ?: 0

            YarnEditScreen(
                navController = navController,
                brand = brand,
                name = name,
                colorway = colorway,
                yardage = yardage,
                weight = weight,
                skeins = skeins
            )
        }

        composable(Screen.NeedlesList.route) {
            NeedlesListScreen(navController)
        }

        composable(
            route = Screen.NeedlesDetail.route,
            arguments = listOf(
                navArgument("brand") { type = NavType.StringType },
                navArgument("size") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("length") { type = NavType.IntType },
                navArgument("material") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val brand = backStackEntry.arguments?.getString("brand") ?: ""
            val size = backStackEntry.arguments?.getString("size") ?: ""
            val type = backStackEntry.arguments?.getString("type") ?: ""
            val length = backStackEntry.arguments?.getInt("length") ?: 0
            val material = backStackEntry.arguments?.getString("material") ?: ""

            NeedlesDetailScreen(
                navController = navController,
                brand = brand,
                size = size,
                type = type,
                length = length,
                material = material
            )
        }

        composable(
            route = Screen.NeedlesEdit.route,
            arguments = listOf(
                navArgument("brand") { type = NavType.StringType },
                navArgument("size") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("length") { type = NavType.IntType },
                navArgument("material") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val brand = backStackEntry.arguments?.getString("brand") ?: ""
            val size = backStackEntry.arguments?.getString("size") ?: ""
            val type = backStackEntry.arguments?.getString("type") ?: ""
            val length = backStackEntry.arguments?.getInt("length") ?: 0
            val material = backStackEntry.arguments?.getString("material") ?: ""

            NeedlesEditScreen(
                navController = navController,
                brand = brand,
                size = size,
                type = type,
                length = length,
                material = material
            )
        }
    }
} 