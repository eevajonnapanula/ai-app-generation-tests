package com.eevajonna.junietestapplication.ui.screens.yarn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eevajonna.junietestapplication.R
import com.eevajonna.junietestapplication.data.model.Yarn

/**
 * Screen that displays a list of all yarns.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnListScreen(
    yarns: List<Yarn>,
    onYarnClick: (Long) -> Unit,
    onAddYarnClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.yarn_list)) },
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
            val addYarnDescription = stringResource(id = R.string.add_yarn)
            FloatingActionButton(
                onClick = onAddYarnClick,
                modifier = Modifier.semantics {
                    contentDescription = addYarnDescription
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
        if (yarns.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No yarns yet. Add some!",
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
                items(yarns) { yarn ->
                    YarnListItem(
                        yarn = yarn,
                        onClick = { onYarnClick(yarn.id) }
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
 * List item for a yarn.
 */
@Composable
fun YarnListItem(
    yarn: Yarn,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                onClickLabel = "${yarn.brandName} ${yarn.yarnName}"
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Yarn image
            if (yarn.pictureUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(yarn.pictureUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp)
                )
            } else {
                // Placeholder image
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Yarn details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${yarn.brandName} ${yarn.yarnName}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = yarn.colorway,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${yarn.amountOfSkeins} skeins",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
