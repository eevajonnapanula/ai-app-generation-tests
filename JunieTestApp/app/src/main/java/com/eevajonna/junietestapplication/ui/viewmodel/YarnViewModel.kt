package com.eevajonna.junietestapplication.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eevajonna.junietestapplication.data.model.Yarn
import com.eevajonna.junietestapplication.data.repository.YarnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Yarn-related UI components.
 */
class YarnViewModel(private val repository: YarnRepository) : ViewModel() {
    
    // All yarns in the database
    val allYarns: Flow<List<Yarn>> = repository.getAllYarns()
    
    // Currently selected yarn for detail view
    private val _selectedYarn = MutableStateFlow<Yarn?>(null)
    val selectedYarn: StateFlow<Yarn?> = _selectedYarn.asStateFlow()
    
    // Form state for adding/editing yarn
    private val _brandName = MutableStateFlow("")
    val brandName: StateFlow<String> = _brandName.asStateFlow()
    
    private val _yarnName = MutableStateFlow("")
    val yarnName: StateFlow<String> = _yarnName.asStateFlow()
    
    private val _colorway = MutableStateFlow("")
    val colorway: StateFlow<String> = _colorway.asStateFlow()
    
    private val _yardageInMeters = MutableStateFlow("")
    val yardageInMeters: StateFlow<String> = _yardageInMeters.asStateFlow()
    
    private val _weightOfSkein = MutableStateFlow("")
    val weightOfSkein: StateFlow<String> = _weightOfSkein.asStateFlow()
    
    private val _amountOfSkeins = MutableStateFlow("")
    val amountOfSkeins: StateFlow<String> = _amountOfSkeins.asStateFlow()
    
    private val _pictureUri = MutableStateFlow<Uri?>(null)
    val pictureUri: StateFlow<Uri?> = _pictureUri.asStateFlow()
    
    // Form validation state
    private val _formValid = MutableStateFlow(false)
    val formValid: StateFlow<Boolean> = _formValid.asStateFlow()
    
    /**
     * Select a yarn for detail view.
     */
    fun selectYarn(id: Long) {
        viewModelScope.launch {
            repository.getYarnById(id).collect { yarn ->
                _selectedYarn.value = yarn
            }
        }
    }
    
    /**
     * Clear the selected yarn.
     */
    fun clearSelectedYarn() {
        _selectedYarn.value = null
    }
    
    /**
     * Set up the form for editing an existing yarn.
     */
    fun setupEditForm(yarn: Yarn) {
        _brandName.value = yarn.brandName
        _yarnName.value = yarn.yarnName
        _colorway.value = yarn.colorway
        _yardageInMeters.value = yarn.yardageInMeters.toString()
        _weightOfSkein.value = yarn.weightOfSkein.toString()
        _amountOfSkeins.value = yarn.amountOfSkeins.toString()
        _pictureUri.value = yarn.pictureUri?.let { Uri.parse(it) }
        validateForm()
    }
    
    /**
     * Clear the form.
     */
    fun clearForm() {
        _brandName.value = ""
        _yarnName.value = ""
        _colorway.value = ""
        _yardageInMeters.value = ""
        _weightOfSkein.value = ""
        _amountOfSkeins.value = ""
        _pictureUri.value = null
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
     * Update the yarn name.
     */
    fun updateYarnName(value: String) {
        _yarnName.value = value
        validateForm()
    }
    
    /**
     * Update the colorway.
     */
    fun updateColorway(value: String) {
        _colorway.value = value
        validateForm()
    }
    
    /**
     * Update the yardage in meters.
     */
    fun updateYardageInMeters(value: String) {
        _yardageInMeters.value = value
        validateForm()
    }
    
    /**
     * Update the weight of skein.
     */
    fun updateWeightOfSkein(value: String) {
        _weightOfSkein.value = value
        validateForm()
    }
    
    /**
     * Update the amount of skeins.
     */
    fun updateAmountOfSkeins(value: String) {
        _amountOfSkeins.value = value
        validateForm()
    }
    
    /**
     * Update the picture URI.
     */
    fun updatePictureUri(uri: Uri?) {
        _pictureUri.value = uri
    }
    
    /**
     * Validate the form.
     */
    private fun validateForm() {
        val yardageValid = _yardageInMeters.value.toIntOrNull() != null
        val weightValid = _weightOfSkein.value.toIntOrNull() != null
        val amountValid = _amountOfSkeins.value.toIntOrNull() != null
        
        _formValid.value = _brandName.value.isNotBlank() &&
                _yarnName.value.isNotBlank() &&
                _colorway.value.isNotBlank() &&
                yardageValid && weightValid && amountValid
    }
    
    /**
     * Save the current form as a new yarn or update an existing one.
     */
    fun saveYarn(existingYarn: Yarn? = null) {
        if (!formValid.value) return
        
        val yarn = Yarn(
            id = existingYarn?.id ?: 0,
            brandName = brandName.value,
            yarnName = yarnName.value,
            colorway = colorway.value,
            yardageInMeters = yardageInMeters.value.toInt(),
            weightOfSkein = weightOfSkein.value.toInt(),
            amountOfSkeins = amountOfSkeins.value.toInt(),
            pictureUri = pictureUri.value?.toString()
        )
        
        viewModelScope.launch {
            if (existingYarn == null) {
                repository.insertYarn(yarn)
            } else {
                repository.updateYarn(yarn)
            }
            clearForm()
        }
    }
    
    /**
     * Delete a yarn.
     */
    fun deleteYarn(yarn: Yarn) {
        viewModelScope.launch {
            repository.deleteYarn(yarn)
            if (_selectedYarn.value?.id == yarn.id) {
                clearSelectedYarn()
            }
        }
    }
    
    /**
     * Factory for creating YarnViewModel instances.
     */
    class Factory(private val repository: YarnRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(YarnViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return YarnViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}