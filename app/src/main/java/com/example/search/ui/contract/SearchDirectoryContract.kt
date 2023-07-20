package com.example.search.ui.contract

import com.test.inito.ui.model.Event
import com.test.inito.ui.model.Intention
import com.test.inito.ui.model.ViewState

interface SearchDirectoryContract {

    sealed class SearchDirectoryViewState: ViewState {
        object Loading : SearchDirectoryViewState()

        object Success : SearchDirectoryViewState()

        object Error : SearchDirectoryViewState()
    }

    sealed class SearchDirectoryEvent: Event {
        object OpenSearchScreen : SearchDirectoryEvent()
    }

    sealed class SearchDirectoryIntention: Intention {
        object SearchLabelIntention : SearchDirectoryIntention()
    }
}