package com.example.cursortestapp.ui.screens.needles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.cursortestapp.model.KnittingNeedles
import com.example.cursortestapp.navigation.Screen
import com.example.cursortestapp.ui.components.NeedlesListItem

data class NeedleListItemData(
    val brandName: String,
    val size: String,
    val type: String,
    val lengthCm: Int,
    val material: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeedlesListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Knitting Needles") },
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
                        Screen.NeedlesEdit.createRoute(
                            brand = "",
                            size = "",
                            type = "",
                            length = 0,
                            material = ""
                        )
                    )
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add needle"
                )
            }
        }
    ) { paddingValues ->
        // TODO: Replace with actual data
        val needles = listOf(
            NeedleListItemData(
                brandName = "Brand A",
                size = "3.5mm",
                type = "Single Pointed",
                lengthCm = 35,
                material = "Wood"
            ),
            NeedleListItemData(
                brandName = "Brand B",
                size = "4mm",
                type = "Circular",
                lengthCm = 80,
                material = "Metal"
            ),
            NeedleListItemData(
                brandName = "Brand C",
                size = "2mm",
                type = "Circular",
                lengthCm = 80,
                material = "Metal"
            ),
            NeedleListItemData(
                brandName = "Brand D",
                size = "6mm",
                type = "Circular",
                lengthCm = 80,
                material = "Metal"
            )
        )

        if (needles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No needles in collection")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                needles.forEach { needle ->
                    NeedlesListItem(
                        brandName = needle.brandName,
                        size = needle.size,
                        type = needle.type,
                        lengthCm = needle.lengthCm,
                        material = needle.material,
                        onClick = {
                            navController.navigate(
                                Screen.NeedlesDetail.createRoute(
                                    brand = needle.brandName,
                                    size = needle.size,
                                    type = needle.type,
                                    length = needle.lengthCm,
                                    material = needle.material
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
fun NeedlesListItem(
    needle: KnittingNeedles,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics { 
                contentDescription = "Knitting needles: ${needle.brandName} ${needle.sizeMm}mm ${needle.type}"
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = needle.brandName,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Size: ${needle.sizeMm}mm",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Type: ${needle.type.name.replace("_", " ").lowercase().capitalize()}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
} 