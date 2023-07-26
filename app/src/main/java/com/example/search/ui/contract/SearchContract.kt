package com.example.search.ui.contract

import com.example.search.ui.model.CountriesItem
import com.test.inito.ui.model.Event
import com.test.inito.ui.model.Intention
import com.test.inito.ui.model.ViewState

interface SearchContract {

    sealed class SearchViewState: ViewState {
        object Loading : SearchViewState()

        object Error : SearchViewState()

        data class LoadSearchResults(val details: List<CountriesItem>): SearchViewState()

        data class NoSearchResults(val constraint: String): SearchViewState()
    }

    sealed class SearchEvent: Event {
        object OpenSearchScreen : SearchEvent()
    }

    sealed class SearchIntention: Intention {
        data class SearchCountriesIntention(val constraint: String) : SearchIntention()
    }
}