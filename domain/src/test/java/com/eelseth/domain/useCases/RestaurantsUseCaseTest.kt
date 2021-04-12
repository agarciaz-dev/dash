package com.eelseth.domain.useCases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eelseth.domain.model.Restaurant
import com.eelseth.domain.repository.RestaurantRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
class RestaurantsUseCaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var toothPickRule = ToothPickRule(this, this)

    @MockK
    lateinit var restaurantRepository: RestaurantRepository


    @Inject
    lateinit var useCase: RestaurantsUseCase

    private val restaurantsFlow = MutableStateFlow<List<Restaurant>>(emptyList())

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(TestCoroutineDispatcher())

        every { restaurantRepository.restaurantsFlow() } returns restaurantsFlow

        toothPickRule.scope.installModules(object : Module() {
            init {
                bind(RestaurantRepository::class.java).toInstance(restaurantRepository)
            }
        })

        toothPickRule.inject(this)
    }

    @Test
    fun `verify restaurantsUseCase takes flow from restaurantRepository restaurantsFlow`() =
        runBlockingTest {
            val restaurantA = mockk<Restaurant>(relaxed = true).apply { every { id } returns "a" }
            val restaurantB = mockk<Restaurant>(relaxed = true).apply { every { id } returns "b" }
            val flowEmissions = mutableListOf<List<Restaurant>>()
            val stateObserver = this.launch {
                useCase.invoke().toList(flowEmissions)
            }

            // After first emission
            restaurantsFlow.emit(listOf(restaurantA))
            assertEquals(1, flowEmissions.last().size)
            assertEquals("a", flowEmissions.last()[0].id)

            // After second emission
            restaurantsFlow.emit(listOf(restaurantA, restaurantB))
            assertEquals(2, flowEmissions.last().size)
            assertEquals("a", flowEmissions.last()[0].id)
            assertEquals("b", flowEmissions.last()[1].id)

            stateObserver.cancel()
        }


}