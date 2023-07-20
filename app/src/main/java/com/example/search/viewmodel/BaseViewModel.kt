package com.example.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.inito.ui.model.Event
import com.test.inito.ui.model.Intention
import com.test.inito.ui.model.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS : ViewState, E : Event, I : Intention>(defaultViewState: VS) :
    ViewModel() {
    private val _viewState = MutableStateFlow(defaultViewState)

    val viewState: StateFlow<VS> = _viewState

    private val eventChannel = Channel<E>(Channel.BUFFERED)

    val events = eventChannel.receiveAsFlow()

    /**
     * Updates viewstate in VM
     */
    protected fun updateViewState(stateModifier: (VS) -> VS) {
        _viewState.update(stateModifier)
    }

    /**
     * Fires one-off event
     */
    protected fun fireEvent(event: E) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    /**
     * All intention from view is handled here
     */
    abstract fun onIntention(intention: I): Any?
}