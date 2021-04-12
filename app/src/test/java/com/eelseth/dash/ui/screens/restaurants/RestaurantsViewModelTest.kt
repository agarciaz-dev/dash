package com.eelseth.dash.ui.screens.restaurants

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eelseth.core.model.Result
import com.eelseth.dash.R
import com.eelseth.domain.model.Restaurant
import com.eelseth.domain.useCases.FetchRestaurantsUseCase
import com.eelseth.domain.useCases.RestaurantsUseCase
import com.eelseth.presentation.model.MessageStatus
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import toothpick.config.Module
import toothpick.testing.ToothPickRule
import javax.inject.Inject

@ExperimentalCoroutinesApi
class RestaurantsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var toothPickRule = ToothPickRule(this, this)

    @MockK
    lateinit var restaurantsUseCase: RestaurantsUseCase

    @MockK
    lateinit var fetchRestaurantsUseCase: FetchRestaurantsUseCase

    @Inject
    lateinit var viewModel: RestaurantsViewModel

    private val restaurantsFlow = MutableStateFlow<List<Restaurant>>(emptyList())

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(TestCoroutineDispatcher())

        every { restaurantsUseCase.invoke() } returns restaurantsFlow
        coEvery { fetchRestaurantsUseCase.invoke() } returns Result.Success(Unit)

        toothPickRule.scope.installModules(object : Module() {
            init {
                bind(RestaurantsUseCase::class.java).toInstance(restaurantsUseCase)
                bind(FetchRestaurantsUseCase::class.java).toInstance(fetchRestaurantsUseCase)
            }
        })

        toothPickRule.inject(this)
    }

    @Test
    fun `verify restaurants are set in the state after restaurantsUseCase flow emits`() =
        runBlockingTest {
            val restaurantA = mockk<Restaurant>(relaxed = true).apply { every { id } returns "a" }
            val restaurantB = mockk<Restaurant>(relaxed = true).apply { every { id } returns "b" }

            viewModel.onEvent(RestaurantsViewModel.View.Event.Init)

            // Initial restaurants data state
            assertEquals(emptyList<Restaurant>(), viewModel.state.value.restaurants)

            // After first emission
            restaurantsFlow.emit(listOf(restaurantA))
            assertEquals(1, viewModel.state.value.restaurants.size)
            assertEquals("a", viewModel.state.value.restaurants[0].id)

            // After second emission
            restaurantsFlow.emit(listOf(restaurantA, restaurantB))
            assertEquals(2, viewModel.state.value.restaurants.size)
            assertEquals("a", viewModel.state.value.restaurants[0].id)
            assertEquals("b", viewModel.state.value.restaurants[1].id)
        }

    @Test
    fun `verify fetchRestaurants is called on refresh event`() =
        runBlockingTest {
            viewModel.onEvent(RestaurantsViewModel.View.Event.Refresh)
            coVerify(exactly = 1) { fetchRestaurantsUseCase.invoke() }
            viewModel.onEvent(RestaurantsViewModel.View.Event.Refresh)
            coVerify(exactly = 2) { fetchRestaurantsUseCase.invoke() }
        }

    @Test
    fun `verify error message is shown if fetchRestaurants fails`() =
        runBlockingTest {
            val effectEmissions = mutableListOf<RestaurantsViewModel.View.Effect>()
            val stateObserver = this.launch {
                viewModel.effect.toList(effectEmissions)
            }

            coEvery { fetchRestaurantsUseCase.invoke() } returns Result.Error(mockk())
            viewModel.onEvent(RestaurantsViewModel.View.Event.Init)

            // Verify flow values stream
            assertEquals(1, effectEmissions.size)
            assertEquals(
                RestaurantsViewModel.View.Effect.ShowMessage(
                    R.string.general_request_error,
                    MessageStatus.Error
                ), effectEmissions.first()
            )

            stateObserver.cancel()
        }

}