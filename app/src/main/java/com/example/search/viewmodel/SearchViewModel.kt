package com.example.search.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.search.ui.contract.SearchContract.*
import com.example.search.utils.SearchHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() :
    BaseViewModel<SearchViewState, SearchEvent, SearchIntention>(
        SearchViewState.Loading
    ) {

    private lateinit var helper: SearchHelper

    companion object {
        private val TAG = SearchViewModel::class.simpleName
    }

    init {
        updateViewState {
            SearchViewState.LoadSearchResults(emptyList())
        }
        helper = SearchHelper(emptyList())
    }

    override fun onIntention(intention: SearchIntention): Any? = when (intention) {
        is SearchIntention.SearchCountriesIntention -> performSearch(intention.constraint)
    }

    private fun performSearch(constraint: String?) = viewModelScope.launch(Dispatchers.Default) {
        if (constraint.isNullOrEmpty()) {
            updateViewState {
                SearchViewState.NoSearchResults(constraint)
            }
        } else {
            val details = helper.performSearch(constraint)

            if (details.isEmpty()) {
                updateViewState {
                    SearchViewState.NoSearchResults(constraint)
                }
            } else {
                updateViewState {
                    SearchViewState.LoadSearchResults(details)
                }
            }
        }
    }
}