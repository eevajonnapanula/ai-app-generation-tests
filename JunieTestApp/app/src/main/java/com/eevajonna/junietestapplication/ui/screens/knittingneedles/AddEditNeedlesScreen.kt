package com.eevajonna.junietestapplication.ui.screens.knittingneedles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.eevajonna.junietestapplication.R
import com.eevajonna.junietestapplication.data.model.NeedleType
import com.eevajonna.junietestapplication.ui.viewmodel.KnittingNeedlesViewModel

/**
 * Screen for adding or editing knitting needles.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNeedlesScreen(
    viewModel: KnittingNeedlesViewModel,
    isEditing: Boolean,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val brandName by viewModel.brandName.collectAsState()
    val sizeInMm by viewModel.sizeInMm.collectAsState()
    val needleType by viewModel.needleType.collectAsState()
    val formValid by viewModel.formValid.collectAsState()
    
    val title = stringResource(
        id = if (isEditing) R.string.edit_needles else R.string.add_new_needles
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    val backDescription = stringResource(id = R.string.back)
                    IconButton(
                        onClick = onCancelClick,
                        modifier = Modifier.semantics {
                            contentDescription = backDescription
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Form fields
            OutlinedTextField(
                value = brandName,
                onValueChange = { viewModel.updateBrandName(it) },
                label = { Text(stringResource(id = R.string.brand_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = sizeInMm,
                onValueChange = { viewModel.updateSizeInMm(it) },
                label = { Text(stringResource(id = R.string.size_in_mm)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Needle type dropdown
            var expanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = NeedleType.getDisplayName(needleType),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.needle_type)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )
                
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    viewModel.needleTypeDisplayNames.forEach { displayName ->
                        DropdownMenuItem(
                            text = { Text(text = displayName) },
                            onClick = {
                                viewModel.updateNeedleTypeFromDisplayName(displayName)
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onCancelClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
                
                Button(
                    onClick = onSaveClick,
                    enabled = formValid,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(id = R.string.save))
                }
            }
            
            // Add some space at the bottom
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}