package com.example.search.viewmodel

import com.example.search.ui.contract.SearchDirectoryContract.*
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SearchDirectoryViewModel :
    BaseViewModel<SearchDirectoryViewState, SearchDirectoryEvent, SearchDirectoryIntention>(
        SearchDirectoryViewState.Loading
    ) {

    companion object {
        private val TAG = SearchDirectoryViewModel::class.simpleName
    }

    override fun onIntention(intention: SearchDirectoryIntention): Any? = when (intention) {
        SearchDirectoryIntention.SearchLabelIntention -> fireEvent(
            SearchDirectoryEvent.OpenSearchScreen
        )
    }
}