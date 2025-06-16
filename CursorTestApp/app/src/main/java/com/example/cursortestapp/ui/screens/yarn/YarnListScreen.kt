package com.example.cursortestapp.ui.screens.yarn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cursortestapp.model.Yarn
import com.example.cursortestapp.navigation.Screen
import com.example.cursortestapp.ui.components.YarnListItem

data class YarnListItemData(
    val brandName: String,
    val yarnName: String,
    val colorway: String,
    val yardageMeters: Int,
    val weightGrams: Int,
    val skeinCount: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yarn Stash") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.NeedlesList.route) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "View knitting needles"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        Screen.YarnEdit.createRoute(
                            brand = "",
                            name = "",
                            colorway = "",
                            yardage = 0,
                            weight = 0,
                            skeins = 0
                        )
                    )
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add yarn"
                )
            }
        }
    ) { paddingValues ->
        // TODO: Replace with actual data
        val yarns = listOf(
            YarnListItemData(
                brandName = "Brand A",
                yarnName = "Yarn 1",
                colorway = "Blue",
                yardageMeters = 100,
                weightGrams = 50,
                skeinCount = 2
            ),
            YarnListItemData(
                brandName = "Brand B",
                yarnName = "Yarn 2",
                colorway = "Red",
                yardageMeters = 200,
                weightGrams = 100,
                skeinCount = 1
            )
        )

        if (yarns.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No yarn in stash")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                yarns.forEach { yarn ->
                    YarnListItem(
                        brandName = yarn.brandName,
                        yarnName = yarn.yarnName,
                        colorway = yarn.colorway,
                        yardageMeters = yarn.yardageMeters,
                        weightGrams = yarn.weightGrams,
                        skeinCount = yarn.skeinCount,
                        onClick = {
                            navController.navigate(
                                Screen.YarnDetail.createRoute(
                                    brand = yarn.brandName,
                                    name = yarn.yarnName,
                                    colorway = yarn.colorway,
                                    yardage = yarn.yardageMeters,
                                    weight = yarn.weightGrams,
                                    skeins = yarn.skeinCount
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnListItem(
    yarn: Yarn,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics { 
                contentDescription = "Yarn: ${yarn.brandName} ${yarn.yarnName} in ${yarn.colorway}"
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${yarn.brandName} - ${yarn.yarnName}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Colorway: ${yarn.colorway}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${yarn.yardageMeters}m, ${yarn.weightGrams}g, ${yarn.skeinCount} skeins",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
} 