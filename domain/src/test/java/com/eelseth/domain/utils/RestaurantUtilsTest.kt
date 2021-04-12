package com.eelseth.domain.utils

import com.eelseth.domain.model.Restaurant
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.*

class RestaurantUtilsTest {

    @MockK
    lateinit var calendar: Calendar

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar
    }

    @Test
    fun `check if restaurant is open or closed`() {

        mockk<Restaurant>(relaxed = true).apply {
            every { nextOpenTimestamp } returns 1617616800000 //Mon Apr 05 2021 10:00:00 UTC
            every { nextCloseTimestamp } returns 1617634800000 //Mon Apr 05 2021 15:00:00 UTC
        }.let { validRestaurant ->
            //Open
            every { calendar.timeInMillis } returns 1617624000000 //Mon Apr 05 2021 12:00:00
            assertTrue(validRestaurant.isOpen())

            //Closed
            every { calendar.timeInMillis } returns 1617613200000 //Mon Apr 05 2021 09:00:00
            assertFalse(validRestaurant.isOpen())

            //Closed
            every { calendar.timeInMillis } returns 1617652800000 //Mon Apr 05 2021 20:00:00
            assertFalse(validRestaurant.isOpen())
        }

        mockk<Restaurant>(relaxed = true).apply {
            every { nextOpenTimestamp } returns null
            every { nextCloseTimestamp } returns 1617634800000 //Mon Apr 05 2021 15:00:00 UTC
        }.let { invalidRestaurant ->
            //Closed
            every { calendar.timeInMillis } returns 1617613200000 //Mon Apr 05 2021 09:00:00
            assertFalse(invalidRestaurant.isOpen())
        }
    }


}