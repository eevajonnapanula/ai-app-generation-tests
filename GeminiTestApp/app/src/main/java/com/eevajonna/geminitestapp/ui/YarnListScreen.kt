package com.eevajonna.geminitestapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eevajonna.geminitestapp.data.Yarn
import com.eevajonna.geminitestapp.R
import com.eevajonna.geminitestapp.Screen
import com.eevajonna.geminitestapp.viewmodel.YarnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnListScreen(navController: NavHostController, viewModel: YarnViewModel) {
    val yarns by viewModel.yarns.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.yarn_list_title),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.semantics { heading() }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.YarnAdd.name) }) {
                Icon(Icons.Filled.Add, "Add Yarn")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(yarns) { yarn ->
                YarnListItem(yarn, onClick = {
                    viewModel.selectYarn(yarn)
                    navController.navigate(Screen.YarnDetails.name)
                })
            }
        }
    }
}

@Composable
fun YarnListItem(yarn: Yarn, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
            .semantics { contentDescription = "${yarn.brand} ${yarn.name} ${yarn.colorway}" }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${yarn.brand} - ${yarn.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = yarn.colorway, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
