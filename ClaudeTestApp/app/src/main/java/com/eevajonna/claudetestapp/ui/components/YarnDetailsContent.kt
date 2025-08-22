package com.eevajonna.claudetestapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.eevajonna.claudetestapp.data.model.Yarn

@Composable
fun YarnDetailsContent(yarn: Yarn) {
    val totalYardage = yarn.yardageMeters * yarn.skeinCount
    val totalWeight = yarn.weightGrams * yarn.skeinCount

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Yarn Details",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )

            DetailRow("Brand", yarn.brandName)
            DetailRow("Yarn Name", yarn.yarnName)
            DetailRow("Colorway", yarn.colorway)
            DetailRow("Number of Skeins", yarn.skeinCount.toString())
            DetailRow("Yardage per Skein", "${yarn.yardageMeters} meters")
            DetailRow("Weight per Skein", "${yarn.weightGrams} grams")
        }
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Total Available",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            DetailRow(
                label = "Total Yardage",
                value = "$totalYardage meters",
                valueColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
            DetailRow(
                label = "Total Weight",
                value = "$totalWeight grams",
                valueColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}