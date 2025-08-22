package com.eevajonna.claudetestapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eevajonna.claudetestapp.data.model.KnittingNeedle
import com.eevajonna.claudetestapp.ui.components.NeedleListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeedleListScreen(
    navController: NavHostController,
    needles: List<KnittingNeedle>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Knitting Needles") },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.semantics {
                        contentDescription = "Go back to home screen"
                    }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(
                    onClick = { navController.navigate("search") },
                    modifier = Modifier.semantics {
                        contentDescription = "Search yarns and needles"
                    }
                ) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                FloatingActionButton(
                    onClick = { navController.navigate("add_needle") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            contentDescription = "Add new knitting needles to collection"
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add New Needles")
                    }
                }
            }

            items(needles) { needle ->
                NeedleListItem(
                    needle = needle,
                    onClick = { navController.navigate("needle_details/${needle.id}") }
                )
            }
        }
    }
}
