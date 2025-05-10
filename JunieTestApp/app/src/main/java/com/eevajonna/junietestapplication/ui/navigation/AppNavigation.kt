package com.eevajonna.junietestapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eevajonna.junietestapplication.data.AppDatabase
import com.eevajonna.junietestapplication.ui.screens.home.HomeScreen
import com.eevajonna.junietestapplication.ui.screens.knittingneedles.AddEditNeedlesScreen
import com.eevajonna.junietestapplication.ui.screens.knittingneedles.NeedlesDetailScreen
import com.eevajonna.junietestapplication.ui.screens.knittingneedles.NeedlesListScreen
import com.eevajonna.junietestapplication.ui.screens.yarn.AddEditYarnScreen
import com.eevajonna.junietestapplication.ui.screens.yarn.YarnDetailScreen
import com.eevajonna.junietestapplication.ui.screens.yarn.YarnListScreen
import com.eevajonna.junietestapplication.ui.viewmodel.KnittingNeedlesViewModel
import com.eevajonna.junietestapplication.ui.viewmodel.SharedViewModel
import com.eevajonna.junietestapplication.ui.viewmodel.YarnViewModel

/**
 * Main navigation component for the app.
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    database: AppDatabase,
    modifier: Modifier = Modifier
) {
    // Create shared view model
    val sharedViewModel: SharedViewModel = viewModel(
        factory = SharedViewModel.Factory(database)
    )
    
    // Create view models for yarn and knitting needles
    val yarnViewModel: YarnViewModel = viewModel(
        factory = sharedViewModel.getYarnViewModel()
    )
    
    val knittingNeedlesViewModel: KnittingNeedlesViewModel = viewModel(
        factory = sharedViewModel.getKnittingNeedlesViewModel()
    )
    
    // Set up navigation
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Home screen
        composable(Screen.Home.route) {
            sharedViewModel.updateCurrentRoute(Screen.Home.route)
            HomeScreen(
                onNavigateToYarnList = { navController.navigate(Screen.YarnList.route) },
                onNavigateToNeedlesList = { navController.navigate(Screen.NeedlesList.route) },
                onNavigateToAddYarn = { navController.navigate(Screen.AddYarn.route) },
                onNavigateToAddNeedles = { navController.navigate(Screen.AddNeedles.route) }
            )
        }
        
        // Yarn list screen
        composable(Screen.YarnList.route) {
            sharedViewModel.updateCurrentRoute(Screen.YarnList.route)
            val yarns by yarnViewModel.allYarns.collectAsState(initial = emptyList())
            YarnListScreen(
                yarns = yarns,
                onYarnClick = { yarnId ->
                    navController.navigate(Screen.YarnDetail.createRoute(yarnId))
                },
                onAddYarnClick = { navController.navigate(Screen.AddYarn.route) },
                onBackClick = { navController.navigateUp() }
            )
        }
        
        // Yarn detail screen
        composable(
            route = Screen.YarnDetail.route,
            arguments = listOf(navArgument("yarnId") { type = NavType.LongType })
        ) { backStackEntry ->
            sharedViewModel.updateCurrentRoute(Screen.YarnDetail.route)
            val yarnId = backStackEntry.arguments?.getLong("yarnId") ?: return@composable
            yarnViewModel.selectYarn(yarnId)
            val yarn by yarnViewModel.selectedYarn.collectAsState()
            
            yarn?.let { selectedYarn ->
                YarnDetailScreen(
                    yarn = selectedYarn,
                    onEditClick = {
                        navController.navigate(Screen.EditYarn.createRoute(selectedYarn.id))
                    },
                    onDeleteClick = {
                        yarnViewModel.deleteYarn(selectedYarn)
                        navController.navigateUp()
                    },
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
        
        // Add yarn screen
        composable(Screen.AddYarn.route) {
            sharedViewModel.updateCurrentRoute(Screen.AddYarn.route)
            yarnViewModel.clearForm()
            AddEditYarnScreen(
                viewModel = yarnViewModel,
                isEditing = false,
                onSaveClick = {
                    yarnViewModel.saveYarn()
                    navController.navigateUp()
                },
                onCancelClick = { navController.navigateUp() }
            )
        }
        
        // Edit yarn screen
        composable(
            route = Screen.EditYarn.route,
            arguments = listOf(navArgument("yarnId") { type = NavType.LongType })
        ) { backStackEntry ->
            sharedViewModel.updateCurrentRoute(Screen.EditYarn.route)
            val yarnId = backStackEntry.arguments?.getLong("yarnId") ?: return@composable
            yarnViewModel.selectYarn(yarnId)
            val yarn by yarnViewModel.selectedYarn.collectAsState()
            
            yarn?.let { selectedYarn ->
                // Set up form with existing yarn data
                yarnViewModel.setupEditForm(selectedYarn)
                
                AddEditYarnScreen(
                    viewModel = yarnViewModel,
                    isEditing = true,
                    onSaveClick = {
                        yarnViewModel.saveYarn(selectedYarn)
                        navController.navigateUp()
                    },
                    onCancelClick = { navController.navigateUp() }
                )
            }
        }
        
        // Knitting needles list screen
        composable(Screen.NeedlesList.route) {
            sharedViewModel.updateCurrentRoute(Screen.NeedlesList.route)
            val needles by knittingNeedlesViewModel.allKnittingNeedles.collectAsState(initial = emptyList())
            NeedlesListScreen(
                needles = needles,
                onNeedlesClick = { needlesId ->
                    navController.navigate(Screen.NeedlesDetail.createRoute(needlesId))
                },
                onAddNeedlesClick = { navController.navigate(Screen.AddNeedles.route) },
                onBackClick = { navController.navigateUp() }
            )
        }
        
        // Knitting needles detail screen
        composable(
            route = Screen.NeedlesDetail.route,
            arguments = listOf(navArgument("needlesId") { type = NavType.LongType })
        ) { backStackEntry ->
            sharedViewModel.updateCurrentRoute(Screen.NeedlesDetail.route)
            val needlesId = backStackEntry.arguments?.getLong("needlesId") ?: return@composable
            knittingNeedlesViewModel.selectKnittingNeedles(needlesId)
            val needles by knittingNeedlesViewModel.selectedKnittingNeedles.collectAsState()
            
            needles?.let { selectedNeedles ->
                NeedlesDetailScreen(
                    needles = selectedNeedles,
                    onEditClick = {
                        navController.navigate(Screen.EditNeedles.createRoute(selectedNeedles.id))
                    },
                    onDeleteClick = {
                        knittingNeedlesViewModel.deleteKnittingNeedles(selectedNeedles)
                        navController.navigateUp()
                    },
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
        
        // Add knitting needles screen
        composable(Screen.AddNeedles.route) {
            sharedViewModel.updateCurrentRoute(Screen.AddNeedles.route)
            knittingNeedlesViewModel.clearForm()
            AddEditNeedlesScreen(
                viewModel = knittingNeedlesViewModel,
                isEditing = false,
                onSaveClick = {
                    knittingNeedlesViewModel.saveKnittingNeedles()
                    navController.navigateUp()
                },
                onCancelClick = { navController.navigateUp() }
            )
        }
        
        // Edit knitting needles screen
        composable(
            route = Screen.EditNeedles.route,
            arguments = listOf(navArgument("needlesId") { type = NavType.LongType })
        ) { backStackEntry ->
            sharedViewModel.updateCurrentRoute(Screen.EditNeedles.route)
            val needlesId = backStackEntry.arguments?.getLong("needlesId") ?: return@composable
            knittingNeedlesViewModel.selectKnittingNeedles(needlesId)
            val needles by knittingNeedlesViewModel.selectedKnittingNeedles.collectAsState()
            
            needles?.let { selectedNeedles ->
                // Set up form with existing needles data
                knittingNeedlesViewModel.setupEditForm(selectedNeedles)
                
                AddEditNeedlesScreen(
                    viewModel = knittingNeedlesViewModel,
                    isEditing = true,
                    onSaveClick = {
                        knittingNeedlesViewModel.saveKnittingNeedles(selectedNeedles)
                        navController.navigateUp()
                    },
                    onCancelClick = { navController.navigateUp() }
                )
            }
        }
    }
}