package com.dgituserapi2.utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dgituserapi2.theme.ThemePreference
import com.dgituserapi2.theme.ThemeViewModel


class ViewModelFactoryTheme(private val pref: ThemePreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class: " + modelClass.name)
    }
}