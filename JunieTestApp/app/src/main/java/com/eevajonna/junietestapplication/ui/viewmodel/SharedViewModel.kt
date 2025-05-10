package com.eevajonna.junietestapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eevajonna.junietestapplication.data.AppDatabase
import com.eevajonna.junietestapplication.data.repository.KnittingNeedlesRepository
import com.eevajonna.junietestapplication.data.repository.YarnRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Shared ViewModel for app-wide state and navigation.
 */
class SharedViewModel(
    private val yarnRepository: YarnRepository,
    private val knittingNeedlesRepository: KnittingNeedlesRepository
) : ViewModel() {
    
    // Navigation state
    private val _currentRoute = MutableStateFlow<String?>(null)
    val currentRoute: StateFlow<String?> = _currentRoute.asStateFlow()
    
    /**
     * Update the current route.
     */
    fun updateCurrentRoute(route: String) {
        _currentRoute.value = route
    }
    
    /**
     * Factory for creating SharedViewModel instances.
     */
    class Factory(private val database: AppDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SharedViewModel(
                    YarnRepository(database.yarnDao()),
                    KnittingNeedlesRepository(database.knittingNeedlesDao())
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    
    /**
     * Get a YarnViewModel instance.
     */
    fun getYarnViewModel(): YarnViewModel.Factory {
        return YarnViewModel.Factory(yarnRepository)
    }
    
    /**
     * Get a KnittingNeedlesViewModel instance.
     */
    fun getKnittingNeedlesViewModel(): KnittingNeedlesViewModel.Factory {
        return KnittingNeedlesViewModel.Factory(knittingNeedlesRepository)
    }
}