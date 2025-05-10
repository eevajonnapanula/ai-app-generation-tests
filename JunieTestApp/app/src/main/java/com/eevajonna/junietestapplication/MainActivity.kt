package com.eevajonna.junietestapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.eevajonna.junietestapplication.data.AppDatabase
import com.eevajonna.junietestapplication.ui.components.AppScaffold
import com.eevajonna.junietestapplication.ui.navigation.AppNavigation
import com.eevajonna.junietestapplication.ui.theme.JunieTestApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Get the database instance
        val database = AppDatabase.getDatabase(applicationContext)

        setContent {
            JunieTestApplicationTheme {
                YarnStashApp(database)
            }
        }
    }
}

@Composable
fun YarnStashApp(database: AppDatabase) {
    val navController = rememberNavController()

    AppScaffold(navController = navController) { innerPadding ->
        AppNavigation(
            navController = navController,
            database = database,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun YarnStashAppPreview() {
    // This preview won't work properly because it needs a real database instance
    // It's just here as a placeholder
    JunieTestApplicationTheme {
        // Empty preview
    }
}
