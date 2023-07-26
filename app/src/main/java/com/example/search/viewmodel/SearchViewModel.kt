package com.example.search.viewmodel

import com.example.search.ui.contract.SearchContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor():
    BaseViewModel<SearchViewState, SearchEvent, SearchIntention>(
        SearchViewState.Loading
    ) {

    companion object {
        private val TAG = SearchViewModel::class.simpleName
    }

    init {
        updateViewState {
            SearchViewState.LoadSearchResults(emptyList())
        }
    }

    override fun onIntention(intention: SearchIntention): Any? = when (intention) {
        is SearchIntention.SearchCountriesIntention -> fireEvent(
            SearchEvent.OpenSearchScreen
        )
    }
}