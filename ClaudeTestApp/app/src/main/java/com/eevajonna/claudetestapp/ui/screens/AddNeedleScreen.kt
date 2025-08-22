package com.eevajonna.claudetestapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.eevajonna.claudetestapp.data.model.KnittingNeedle
import com.eevajonna.claudetestapp.data.model.NeedleType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNeedleScreen(
    navController: NavHostController,
    onNeedleAdded: (KnittingNeedle) -> Unit
) {
    var brandName by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(NeedleType.CIRCULAR) }
    var expanded by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Add New Needles") },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.semantics {
                        contentDescription = "Cancel adding needles and go back"
                    }
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            },
            actions = {
                TextButton(
                    onClick = {
                        // Validate and save
                        val sizeDouble = size.toDoubleOrNull() ?: 0.0

                        if (brandName.isNotBlank() && sizeDouble > 0) {
                            val newNeedle = KnittingNeedle(
                                id = System.currentTimeMillis().toString(),
                                brandName = brandName.trim(),
                                sizeMillimeters = sizeDouble,
                                type = selectedType
                            )
                            onNeedleAdded(newNeedle)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.semantics {
                        contentDescription = "Save needles to collection"
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
            // Large needle icon
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "Knitting needle illustration",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
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
                value = size,
                onValueChange = { size = it },
                label = { Text("Size (mm) *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Needle size in millimeters, required field"
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Select needle type"
                    }
            ) {
                OutlinedTextField(
                    value = selectedType.displayName,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Needle Type *") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    NeedleType.values().forEach { type ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = type.displayName,
                                    modifier = Modifier.semantics {
                                        contentDescription = "Select ${type.displayName} needle type"
                                    }
                                )
                            },
                            onClick = {
                                selectedType = type
                                expanded = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "* Required fields",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}