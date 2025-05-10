package com.eevajonna.junietestapplication.ui.screens.yarn

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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eevajonna.junietestapplication.R
import com.eevajonna.junietestapplication.data.model.Yarn

/**
 * Screen that displays the details of a specific yarn.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnDetailScreen(
    yarn: Yarn,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.yarn_details)) },
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
            // Yarn image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (yarn.pictureUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(yarn.pictureUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "${yarn.brandName} ${yarn.yarnName}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Placeholder image
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Yarn details
            Text(
                text = "${yarn.brandName} ${yarn.yarnName}",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = yarn.colorway,
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Yarn specifications
            DetailRow(
                label = stringResource(id = R.string.yardage_in_meters),
                value = "${yarn.yardageInMeters} m"
            )
            
            DetailRow(
                label = stringResource(id = R.string.weight_of_skein),
                value = "${yarn.weightOfSkein} g"
            )
            
            DetailRow(
                label = stringResource(id = R.string.amount_of_skeins),
                value = "${yarn.amountOfSkeins}"
            )
        }
        
        // Delete confirmation dialog
        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Delete Yarn") },
                text = { Text("Are you sure you want to delete this yarn?") },
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

/**
 * Row displaying a label and value.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}