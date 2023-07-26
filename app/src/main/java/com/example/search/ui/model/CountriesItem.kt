package com.example.search.ui.model

import androidx.recyclerview.widget.DiffUtil

sealed class CountriesItem(val id: String) {
    data class HeaderItem(val heading: String) : CountriesItem("heading")

    data class CountriesDetailsItem(
        val countryCode: String,
        val country: String,
        val region: String
    ) : CountriesItem(countryCode)

    companion object : DiffUtil.ItemCallback<CountriesItem>() {
        override fun areItemsTheSame(oldItem: CountriesItem, newItem: CountriesItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CountriesItem, newItem: CountriesItem) =
            oldItem == newItem
    }
}
