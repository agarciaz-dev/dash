package com.eelseth.dash.ui.screens.restaurants

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eelseth.core.model.error
import com.eelseth.dash.R
import com.eelseth.domain.model.Restaurant
import com.eelseth.domain.useCases.FetchRestaurantsUseCase
import com.eelseth.domain.useCases.RestaurantsUseCase
import com.eelseth.presentation.model.MessageStatus
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import toothpick.InjectConstructor

@InjectConstructor
class RestaurantsViewModel(
    private val restaurantsUseCase: RestaurantsUseCase,
    private val fetchRestaurantsUseCase: FetchRestaurantsUseCase
) : ViewModel() {

    val state: StateFlow<State> get() = _state
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState())

    val effect: SharedFlow<Effect> get() = _effect
    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()

    fun onEvent(event: Event.Init) {
        bindRestaurants()
        fetchRestaurants()
    }

    fun onEvent(event: Event.Refresh) {
        fetchRestaurants()
    }

    fun onEvent(event: Event.RestaurantSelected) {
        viewModelScope.launch {
            _effect.emit(Effect.GoToRestaurantDetail(event.restaurantId))
        }
    }

    private fun bindRestaurants() {
        viewModelScope.launch {
            restaurantsUseCase.invoke().collect {
                _state.value = _state.value.copy(restaurants = it)
            }
        }
    }

    private fun fetchRestaurants() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingRequest = true)
            fetchRestaurantsUseCase.invoke().error()?.let {
                _effect.emit(
                    Effect.ShowMessage(
                        R.string.general_request_error,
                        MessageStatus.Error
                    )
                )
            }
            _state.value = _state.value.copy(isLoadingRequest = false)
        }
    }

    private fun initialState(): State = State(
        restaurants = emptyList(),
        isLoadingRequest = false,
    )

    companion object View {
        data class State(
            val restaurants: List<Restaurant>,
            val isLoadingRequest: Boolean,
        )

        sealed class Event {
            object Init : Event()
            object Refresh : Event()
            data class RestaurantSelected(val restaurantId: String) : Event()
        }

        sealed class Effect {
            data class GoToRestaurantDetail(val restaurantId: String) : Effect()
            data class ShowMessage(
                @StringRes val message: Int, val status: MessageStatus
            ) : Effect()
        }
    }


}