package com.example.search.utils

import com.example.search.ui.model.CountriesItem
import javax.inject.Inject

class SearchHelper @Inject constructor(
    private val countriesItem: List<CountriesItem>
) {
    fun performSearch(
        constraint: String
    ): List<CountriesItem> {
        return emptyList()
    }
}