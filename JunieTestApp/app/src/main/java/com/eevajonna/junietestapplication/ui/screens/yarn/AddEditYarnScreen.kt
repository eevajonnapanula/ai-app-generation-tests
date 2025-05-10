package com.eevajonna.junietestapplication.ui.screens.yarn

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eevajonna.junietestapplication.R
import com.eevajonna.junietestapplication.ui.viewmodel.YarnViewModel

/**
 * Screen for adding or editing a yarn.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditYarnScreen(
    viewModel: YarnViewModel,
    isEditing: Boolean,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val brandName by viewModel.brandName.collectAsState()
    val yarnName by viewModel.yarnName.collectAsState()
    val colorway by viewModel.colorway.collectAsState()
    val yardageInMeters by viewModel.yardageInMeters.collectAsState()
    val weightOfSkein by viewModel.weightOfSkein.collectAsState()
    val amountOfSkeins by viewModel.amountOfSkeins.collectAsState()
    val pictureUri by viewModel.pictureUri.collectAsState()
    val formValid by viewModel.formValid.collectAsState()

    val title = stringResource(
        id = if (isEditing) R.string.edit_yarn else R.string.add_new_yarn
    )

    // Image picker
    val context = LocalContext.current
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.updatePictureUri(it) }
    }

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
            // Image picker
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(
                        onClick = { imagePicker.launch("image/*") },
                        onClickLabel = stringResource(id = R.string.select_picture)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (pictureUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(pictureUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.select_picture),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                value = yarnName,
                onValueChange = { viewModel.updateYarnName(it) },
                label = { Text(stringResource(id = R.string.yarn_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = colorway,
                onValueChange = { viewModel.updateColorway(it) },
                label = { Text(stringResource(id = R.string.colorway)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = yardageInMeters,
                onValueChange = { viewModel.updateYardageInMeters(it) },
                label = { Text(stringResource(id = R.string.yardage_in_meters)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weightOfSkein,
                onValueChange = { viewModel.updateWeightOfSkein(it) },
                label = { Text(stringResource(id = R.string.weight_of_skein)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amountOfSkeins,
                onValueChange = { viewModel.updateAmountOfSkeins(it) },
                label = { Text(stringResource(id = R.string.amount_of_skeins)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

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
