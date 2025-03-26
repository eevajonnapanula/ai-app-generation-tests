package com.eevajonna.geminitestapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eevajonna.geminitestapp.R
import com.eevajonna.geminitestapp.ui.components.YarnDetailRow
import com.eevajonna.geminitestapp.ui.components.YarnIconPlaceholder
import com.eevajonna.geminitestapp.viewmodel.YarnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YarnDetailsScreen(navController: NavHostController, viewModel: YarnViewModel) {
    val yarn = viewModel.selectedYarn
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.yarn_details_title),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.semantics { heading() }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        if (yarn != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Placeholder for the yarn image/icon
                YarnIconPlaceholder(modifier = Modifier.fillMaxWidth().height(150.dp))
                Spacer(modifier = Modifier.height(16.dp))
                YarnDetailRow(label = stringResource(R.string.yarn_brand_name), value = yarn.brand)
                YarnDetailRow(label = stringResource(R.string.yarn_name), value = yarn.name)
                YarnDetailRow(label = stringResource(R.string.colorway), value = yarn.colorway)
                YarnDetailRow(label = stringResource(R.string.yardage), value = "${yarn.meterage}m")
                YarnDetailRow(label = stringResource(R.string.skein_weight), value = "${yarn.skeinWeight}g")
                YarnDetailRow(label = stringResource(R.string.skeins), value = yarn.skeins.toString())
            }
        } else {
            Text(text = stringResource(R.string
                .no_yarn_selected))
        }
    }
}
