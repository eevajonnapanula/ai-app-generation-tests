package com.eevajonna.claudetestapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eevajonna.claudetestapp.data.model.Yarn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddYarnScreen(
    navController: NavHostController,
    onYarnAdded: (Yarn) -> Unit
) {
    var brandName by remember { mutableStateOf("") }
    var yarnName by remember { mutableStateOf("") }
    var colorway by remember { mutableStateOf("") }
    var yardage by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var skeinCount by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Add New Yarn") },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.semantics {
                        contentDescription = "Cancel adding yarn and go back"
                    }
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            },
            actions = {
                TextButton(
                    onClick = {
                        // Validate and save
                        val yardageInt = yardage.toIntOrNull() ?: 0
                        val weightInt = weight.toIntOrNull() ?: 0
                        val skeinCountInt = skeinCount.toIntOrNull() ?: 1

                        if (brandName.isNotBlank() && yarnName.isNotBlank()) {
                            val newYarn = Yarn(
                                id = System.currentTimeMillis().toString(),
                                brandName = brandName.trim(),
                                yarnName = yarnName.trim(),
                                colorway = colorway.trim().ifBlank { "Unknown" },
                                yardageMeters = yardageInt,
                                weightGrams = weightInt,
                                skeinCount = skeinCountInt
                            )
                            onYarnAdded(newYarn)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.semantics {
                        contentDescription = "Save yarn to collection"
                    }
                ) {
                    Text("Save")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Image placeholder
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                onClick = { /* TODO: Image picker */ }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Camera,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Tap to add photo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            OutlinedTextField(
                value = brandName,
                onValueChange = { brandName = it },
                label = { Text("Brand Name *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Brand name, required field"
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = yarnName,
                onValueChange = { yarnName = it },
                label = { Text("Yarn Name *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Yarn name, required field"
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = colorway,
                onValueChange = { colorway = it },
                label = { Text("Colorway") },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Colorway, optional field"
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = yardage,
                    onValueChange = { yardage = it },
                    label = { Text("Yardage (m)") },
                    modifier = Modifier
                        .weight(1f)
                        .semantics {
                            contentDescription = "Yardage in meters per skein"
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (g)") },
                    modifier = Modifier
                        .weight(1f)
                        .semantics {
                            contentDescription = "Weight in grams per skein"
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = skeinCount,
                onValueChange = { skeinCount = it },
                label = { Text("Number of Skeins") },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Number of skeins you have"
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true
            )

            Text(
                text = "* Required fields",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}