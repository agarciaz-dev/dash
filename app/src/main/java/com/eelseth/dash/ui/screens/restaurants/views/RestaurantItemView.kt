package com.eelseth.dash.ui.screens.restaurants.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.eelseth.dash.R
import com.eelseth.dash.databinding.RestaurantItemViewBinding
import com.eelseth.domain.model.Restaurant
import com.eelseth.domain.utils.isOpen
import com.eelseth.presentation.image.ImageLoader
import com.eelseth.restaurantapp.presentation.image.ImageSource

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class RestaurantItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private val binding = RestaurantItemViewBinding.inflate(LayoutInflater.from(context), this)

    @ModelProp
    lateinit var restaurant: Restaurant

    var onItemClicked: (() -> Unit)? = null
        @CallbackProp set

    var onItemSaved: (() -> Unit)? = null
        @CallbackProp set

    @ModelProp(options = [ModelProp.Option.DoNotHash])
    lateinit var imageLoader: ImageLoader

    @AfterPropsSet
    fun postBindSetup() {
        binding.root.setOnClickListener { onItemClicked?.invoke() }
        binding.restaurantName.text = restaurant.name
        binding.restaurantDescription.text = restaurant.description
        binding.restaurantState.setText(
            if (restaurant.isOpen()) R.string.copy_open else R.string.copy_closed
        )

        binding.saveImageView.setOnClickListener {
            Log.i("events", "saveImageView: ")
            onItemSaved?.invoke()
        }

        imageLoader.loadImage(
            ImageSource.Url(restaurant.coverImageUrl),
            binding.logoImage,
            errorDrawableResource = R.drawable.ic_app_logo,
        )

        if (restaurant.saved) {
            binding.saveImageView.setImageResource(R.drawable.ic_star_selected)
        } else {
            binding.saveImageView.setImageResource(R.drawable.ic_star)
        }
    }
}