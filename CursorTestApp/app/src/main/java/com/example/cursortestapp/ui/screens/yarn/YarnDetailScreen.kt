package com.example.cursortestapp.ui.screens.yarn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.cursortestapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnDetailScreen(
    navController: NavController,
    brand: String,
    name: String,
    colorway: String,
    yardage: Int,
    weight: Int,
    skeins: Int
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yarn Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { 
                            navController.navigate(
                                Screen.YarnEdit.createRoute(
                                    brand = brand,
                                    name = name,
                                    colorway = colorway,
                                    yardage = yardage,
                                    weight = weight,
                                    skeins = skeins
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit yarn details"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            DetailItem(
                label = "Brand",
                value = brand
            )
            DetailItem(
                label = "Yarn Name",
                value = name
            )
            DetailItem(
                label = "Colorway",
                value = colorway
            )
            DetailItem(
                label = "Yardage",
                value = "$yardage meters"
            )
            DetailItem(
                label = "Weight",
                value = "$weight grams"
            )
            DetailItem(
                label = "Skeins",
                value = skeins.toString()
            )
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = "$label: $value"
            }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
} 