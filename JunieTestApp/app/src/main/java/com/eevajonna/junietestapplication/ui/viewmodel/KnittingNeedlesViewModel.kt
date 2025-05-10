package com.eevajonna.junietestapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eevajonna.junietestapplication.data.model.KnittingNeedles
import com.eevajonna.junietestapplication.data.model.NeedleType
import com.eevajonna.junietestapplication.data.repository.KnittingNeedlesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for KnittingNeedles-related UI components.
 */
class KnittingNeedlesViewModel(private val repository: KnittingNeedlesRepository) : ViewModel() {
    
    // All knitting needles in the database
    val allKnittingNeedles: Flow<List<KnittingNeedles>> = repository.getAllKnittingNeedles()
    
    // Currently selected knitting needles for detail view
    private val _selectedKnittingNeedles = MutableStateFlow<KnittingNeedles?>(null)
    val selectedKnittingNeedles: StateFlow<KnittingNeedles?> = _selectedKnittingNeedles.asStateFlow()
    
    // Form state for adding/editing knitting needles
    private val _brandName = MutableStateFlow("")
    val brandName: StateFlow<String> = _brandName.asStateFlow()
    
    private val _sizeInMm = MutableStateFlow("")
    val sizeInMm: StateFlow<String> = _sizeInMm.asStateFlow()
    
    private val _needleType = MutableStateFlow(NeedleType.SINGLE_POINTED)
    val needleType: StateFlow<NeedleType> = _needleType.asStateFlow()
    
    // Form validation state
    private val _formValid = MutableStateFlow(false)
    val formValid: StateFlow<Boolean> = _formValid.asStateFlow()
    
    // List of needle types for dropdown
    val needleTypes = NeedleType.values().toList()
    val needleTypeDisplayNames = NeedleType.getDisplayNames()
    
    /**
     * Select knitting needles for detail view.
     */
    fun selectKnittingNeedles(id: Long) {
        viewModelScope.launch {
            repository.getKnittingNeedlesById(id).collect { knittingNeedles ->
                _selectedKnittingNeedles.value = knittingNeedles
            }
        }
    }
    
    /**
     * Clear the selected knitting needles.
     */
    fun clearSelectedKnittingNeedles() {
        _selectedKnittingNeedles.value = null
    }
    
    /**
     * Get knitting needles by type.
     */
    fun getKnittingNeedlesByType(type: NeedleType): Flow<List<KnittingNeedles>> {
        return repository.getKnittingNeedlesByType(type)
    }
    
    /**
     * Set up the form for editing existing knitting needles.
     */
    fun setupEditForm(knittingNeedles: KnittingNeedles) {
        _brandName.value = knittingNeedles.brandName
        _sizeInMm.value = knittingNeedles.sizeInMm.toString()
        _needleType.value = knittingNeedles.type
        validateForm()
    }
    
    /**
     * Clear the form.
     */
    fun clearForm() {
        _brandName.value = ""
        _sizeInMm.value = ""
        _needleType.value = NeedleType.SINGLE_POINTED
        _formValid.value = false
    }
    
    /**
     * Update the brand name.
     */
    fun updateBrandName(value: String) {
        _brandName.value = value
        validateForm()
    }
    
    /**
     * Update the size in mm.
     */
    fun updateSizeInMm(value: String) {
        _sizeInMm.value = value
        validateForm()
    }
    
    /**
     * Update the needle type.
     */
    fun updateNeedleType(value: NeedleType) {
        _needleType.value = value
    }
    
    /**
     * Update the needle type from display name.
     */
    fun updateNeedleTypeFromDisplayName(displayName: String) {
        _needleType.value = NeedleType.fromDisplayName(displayName)
    }
    
    /**
     * Validate the form.
     */
    private fun validateForm() {
        val sizeValid = _sizeInMm.value.toFloatOrNull() != null
        
        _formValid.value = _brandName.value.isNotBlank() && sizeValid
    }
    
    /**
     * Save the current form as new knitting needles or update existing ones.
     */
    fun saveKnittingNeedles(existingKnittingNeedles: KnittingNeedles? = null) {
        if (!formValid.value) return
        
        val knittingNeedles = KnittingNeedles(
            id = existingKnittingNeedles?.id ?: 0,
            brandName = brandName.value,
            sizeInMm = sizeInMm.value.toFloat(),
            type = needleType.value
        )
        
        viewModelScope.launch {
            if (existingKnittingNeedles == null) {
                repository.insertKnittingNeedles(knittingNeedles)
            } else {
                repository.updateKnittingNeedles(knittingNeedles)
            }
            clearForm()
        }
    }
    
    /**
     * Delete knitting needles.
     */
    fun deleteKnittingNeedles(knittingNeedles: KnittingNeedles) {
        viewModelScope.launch {
            repository.deleteKnittingNeedles(knittingNeedles)
            if (_selectedKnittingNeedles.value?.id == knittingNeedles.id) {
                clearSelectedKnittingNeedles()
            }
        }
    }
    
    /**
     * Factory for creating KnittingNeedlesViewModel instances.
     */
    class Factory(private val repository: KnittingNeedlesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(KnittingNeedlesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return KnittingNeedlesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}