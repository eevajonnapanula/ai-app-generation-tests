package com.example.cursortestapp.ui.screens.yarn


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnEditScreen(
    navController: NavController,
    brand: String = "",
    name: String = "",
    colorway: String = "",
    yardage: Int = 0,
    weight: Int = 0,
    skeins: Int = 0
) {
    var brandName by remember { mutableStateOf(brand) }
    var yarnName by remember { mutableStateOf(name) }
    var yarnColorway by remember { mutableStateOf(colorway) }
    var yarnYardage by remember { mutableStateOf(yardage.toString()) }
    var yarnWeight by remember { mutableStateOf(weight.toString()) }
    var yarnSkeins by remember { mutableStateOf(skeins.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Yarn") },
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
                            // TODO: Save yarn data
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save yarn"
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
            OutlinedTextField(
                value = brandName,
                onValueChange = { brandName = it },
                label = { Text("Brand") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = yarnName,
                onValueChange = { yarnName = it },
                label = { Text("Yarn Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = yarnColorway,
                onValueChange = { yarnColorway = it },
                label = { Text("Colorway") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = yarnYardage,
                onValueChange = { yarnYardage = it },
                label = { Text("Yardage (meters)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = yarnWeight,
                onValueChange = { yarnWeight = it },
                label = { Text("Weight (grams)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = yarnSkeins,
                onValueChange = { yarnSkeins = it },
                label = { Text("Number of Skeins") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
} 