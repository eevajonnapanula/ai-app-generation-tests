package com.eevajonna.junietestapplication.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eevajonna.junietestapplication.R

/**
 * Home screen of the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToYarnList: () -> Unit,
    onNavigateToNeedlesList: () -> Unit,
    onNavigateToAddYarn: () -> Unit,
    onNavigateToAddNeedles: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.welcome_message),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Yarn section
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.yarn),
                        style = MaterialTheme.typography.titleLarge
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val viewYarnDescription = stringResource(id = R.string.view_yarn)
                        Button(
                            onClick = onNavigateToYarnList,
                            modifier = Modifier.semantics {
                                contentDescription = viewYarnDescription
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(id = R.string.view_yarn),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        
                        val addYarnDescription = stringResource(id = R.string.add_yarn)
                        Button(
                            onClick = onNavigateToAddYarn,
                            modifier = Modifier.semantics {
                                contentDescription = addYarnDescription
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(id = R.string.add_yarn),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Needles section
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.needles),
                        style = MaterialTheme.typography.titleLarge
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val viewNeedlesDescription = stringResource(id = R.string.view_needles)
                        Button(
                            onClick = onNavigateToNeedlesList,
                            modifier = Modifier.semantics {
                                contentDescription = viewNeedlesDescription
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(id = R.string.view_needles),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        
                        val addNeedlesDescription = stringResource(id = R.string.add_needles)
                        Button(
                            onClick = onNavigateToAddNeedles,
                            modifier = Modifier.semantics {
                                contentDescription = addNeedlesDescription
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(id = R.string.add_needles),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}