package com.eevajonna.junietestapplication.ui.screens.knittingneedles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.eevajonna.junietestapplication.R
import com.eevajonna.junietestapplication.data.model.KnittingNeedles
import com.eevajonna.junietestapplication.data.model.NeedleType

/**
 * Screen that displays the details of specific knitting needles.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NeedlesDetailScreen(
    needles: KnittingNeedles,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.needles_details)) },
                navigationIcon = {
                    val backDescription = stringResource(id = R.string.back)
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.semantics {
                            contentDescription = backDescription
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    val editDescription = stringResource(id = R.string.edit)
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier.semantics {
                            contentDescription = editDescription
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                    
                    val deleteDescription = stringResource(id = R.string.delete)
                    IconButton(
                        onClick = { showDeleteConfirmation = true },
                        modifier = Modifier.semantics {
                            contentDescription = deleteDescription
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
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
            // Needles details
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Brand name
                    Text(
                        text = needles.brandName,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Size and type
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.size_in_mm),
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "${needles.sizeInMm}mm",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.needle_type),
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = NeedleType.getDisplayName(needles.type),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
        
        // Delete confirmation dialog
        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Delete Knitting Needles") },
                text = { Text("Are you sure you want to delete these knitting needles?") },
                confirmButton = {
                    Button(
                        onClick = {
                            onDeleteClick()
                            showDeleteConfirmation = false
                        }
                    ) {
                        Text(stringResource(id = R.string.delete))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteConfirmation = false }
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }
            )
        }
    }
}