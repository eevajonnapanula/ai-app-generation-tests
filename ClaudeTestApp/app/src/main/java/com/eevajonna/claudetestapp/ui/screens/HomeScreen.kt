package com.eevajonna.claudetestapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eevajonna.claudetestapp.ui.components.QuickActionButton
import com.eevajonna.claudetestapp.ui.components.StatsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    yarnCount: Int,
    needleCount: Int,
    totalSkeins: Int
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Yarn Stash Tracker",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(
                    onClick = { navController.navigate("settings") },
                    modifier = Modifier.semantics {
                        contentDescription = "Open settings"
                    }
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // Statistics Cards
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatsCard(
                    modifier = Modifier.weight(1f),
                    title = "Yarn Collection",
                    count = yarnCount,
                    subtitle = "$totalSkeins total skeins",
                    icon = Icons.Default.Category,
                    onClick = { navController.navigate("yarn_list") }
                )
                StatsCard(
                    modifier = Modifier.weight(1f),
                    title = "Knitting Needles",
                    count = needleCount,
                    subtitle = "Different sizes",
                    icon = Icons.Default.Build,
                    onClick = { navController.navigate("needle_list") }
                )
            }
        } else {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatsCard(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Yarn Collection",
                    count = yarnCount,
                    subtitle = "$totalSkeins total skeins",
                    icon = Icons.Default.Category,
                    onClick = { navController.navigate("yarn_list") }
                )
                StatsCard(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Knitting Needles",
                    count = needleCount,
                    subtitle = "Different sizes",
                    icon = Icons.Default.Build,
                    onClick = { navController.navigate("needle_list") }
                )
            }
        }

        // Quick Actions Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (isLandscape) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        QuickActionButton(
                            modifier = if (isLandscape) Modifier.weight(1f) else Modifier.fillMaxWidth(),
                            text = "Add Yarn",
                            icon = Icons.Default.Add,
                            onClick = { navController.navigate("add_yarn") }
                        )
                        QuickActionButton(
                            modifier = if (isLandscape) Modifier.weight(1f) else Modifier.fillMaxWidth(),
                            text = "Add Needles",
                            icon = Icons.Default.Add,
                            onClick = { navController.navigate("add_needle") }
                        )
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionButton(
                            modifier = if (isLandscape) Modifier.weight(1f) else Modifier.fillMaxWidth(),
                            text = "Add Yarn",
                            icon = Icons.Default.Add,
                            onClick = { navController.navigate("add_yarn") }
                        )
                        QuickActionButton(
                            modifier = if (isLandscape) Modifier.weight(1f) else Modifier.fillMaxWidth(),
                            text = "Add Needles",
                            icon = Icons.Default.Add,
                            onClick = { navController.navigate("add_needle") }
                        )
                    }
                }
            }
        }
    }
}