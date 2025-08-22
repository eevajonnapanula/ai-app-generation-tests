package com.eevajonna.claudetestapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eevajonna.claudetestapp.data.model.KnittingNeedle
import com.eevajonna.claudetestapp.data.model.Yarn
import com.eevajonna.claudetestapp.ui.components.NeedleListItem
import com.eevajonna.claudetestapp.ui.components.YarnListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    yarns: List<Yarn>,
    needles: List<KnittingNeedle>
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    val filteredYarns = remember(searchQuery, yarns) {
        if (searchQuery.isBlank()) yarns else {
            yarns.filter { yarn ->
                yarn.brandName.contains(searchQuery, ignoreCase = true) ||
                        yarn.yarnName.contains(searchQuery, ignoreCase = true) ||
                        yarn.colorway.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val filteredNeedles = remember(searchQuery, needles) {
        if (searchQuery.isBlank()) needles else {
            needles.filter { needle ->
                needle.brandName.contains(searchQuery, ignoreCase = true) ||
                        needle.type.displayName.contains(searchQuery, ignoreCase = true) ||
                        needle.sizeMillimeters.toString().contains(searchQuery)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Search") },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.semantics {
                        contentDescription = "Go back"
                    }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search yarns and needles...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { searchQuery = "" },
                        modifier = Modifier.semantics {
                            contentDescription = "Clear search"
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .semantics {
                    contentDescription = "Search field for yarns and needles"
                },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.semantics {
                contentDescription = "Switch between yarn and needle search results"
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Yarns (${filteredYarns.size})") },
                modifier = Modifier.semantics {
                    contentDescription = "Yarn search results, ${filteredYarns.size} items"
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Needles (${filteredNeedles.size})") },
                modifier = Modifier.semantics {
                    contentDescription = "Needle search results, ${filteredNeedles.size} items"
                }
            )
        }

        // Results
        when (selectedTab) {
            0 -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.semantics {
                        contentDescription = "Yarn search results list"
                    }
                ) {
                    if (filteredYarns.isEmpty() && searchQuery.isNotEmpty()) {
                        item {
                            Text(
                                text = "No yarns found matching \"$searchQuery\"",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp)
                                    .semantics {
                                        contentDescription = "No yarn search results found"
                                    },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        items(filteredYarns) { yarn ->
                            YarnListItem(
                                yarn = yarn,
                                onClick = { navController.navigate("yarn_details/${yarn.id}") }
                            )
                        }
                    }
                }
            }
            1 -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.semantics {
                        contentDescription = "Needle search results list"
                    }
                ) {
                    if (filteredNeedles.isEmpty() && searchQuery.isNotEmpty()) {
                        item {
                            Text(
                                text = "No needles found matching \"$searchQuery\"",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp)
                                    .semantics {
                                        contentDescription = "No needle search results found"
                                    },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        items(filteredNeedles) { needle ->
                            NeedleListItem(
                                needle = needle,
                                onClick = { navController.navigate("needle_details/${needle.id}") }
                            )
                        }
                    }
                }
            }
        }
    }
}