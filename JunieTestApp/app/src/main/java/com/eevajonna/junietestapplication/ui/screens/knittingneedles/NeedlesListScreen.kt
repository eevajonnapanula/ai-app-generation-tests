package com.eevajonna.junietestapplication.ui.screens.knittingneedles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eevajonna.junietestapplication.R
import com.eevajonna.junietestapplication.data.model.KnittingNeedles
import com.eevajonna.junietestapplication.data.model.NeedleType

/**
 * Screen that displays a list of all knitting needles.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeedlesListScreen(
    needles: List<KnittingNeedles>,
    onNeedlesClick: (Long) -> Unit,
    onAddNeedlesClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.needles_list)) },
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
                }
            )
        },
        floatingActionButton = {
            val addNeedlesDescription = stringResource(id = R.string.add_needles)
            FloatingActionButton(
                onClick = onAddNeedlesClick,
                modifier = Modifier.semantics {
                    contentDescription = addNeedlesDescription
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        if (needles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No knitting needles yet. Add some!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                items(needles) { needle ->
                    NeedlesListItem(
                        needles = needle,
                        onClick = { onNeedlesClick(needle.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // Add some space at the bottom for the FAB
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

/**
 * List item for knitting needles.
 */
@Composable
fun NeedlesListItem(
    needles: KnittingNeedles,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                onClickLabel = "${needles.brandName} ${needles.sizeInMm}mm ${NeedleType.getDisplayName(needles.type)}"
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Needle details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = needles.brandName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Row {
                    Text(
                        text = "${needles.sizeInMm}mm",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = NeedleType.getDisplayName(needles.type),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}