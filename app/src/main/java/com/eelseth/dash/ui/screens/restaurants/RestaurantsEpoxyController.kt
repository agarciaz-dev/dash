package com.eelseth.dash.ui.screens.restaurants

import com.airbnb.epoxy.TypedEpoxyController
import com.eelseth.dash.ui.screens.restaurants.views.restaurantItemView
import com.eelseth.domain.model.Restaurant
import com.eelseth.presentation.image.ImageLoader

class RestaurantsEpoxyController(
    private val imageLoader: ImageLoader,
    private var onCommand: ((Command) -> Unit)? = null
) : TypedEpoxyController<List<Restaurant>>() {

    override fun buildModels(data: List<Restaurant>) {

        data.forEach { restaurant ->
            restaurantItemView {
                id(restaurant.id)
                restaurant(restaurant)
                imageLoader(imageLoader)
                onItemClicked {
                    onCommand?.invoke(Command.OnRestaurantSelected(restaurant.id))
                }
                onItemSaved {
                    onCommand?.invoke(Command.OnRestaurantSaved(restaurant.id))
                }
            }
        }
    }

    sealed class Command {
        data class OnRestaurantSelected(val restaurantId: String) : Command()
        data class OnRestaurantSaved(val restaurantId: String) : Command()
    }
}