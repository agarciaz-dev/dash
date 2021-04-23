package com.eelseth.dash.ui.screens.restaurants

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.eelseth.dash.R
import com.eelseth.dash.databinding.RestaurantsFragmentBinding
import com.eelseth.domain.model.Restaurant
import com.eelseth.presentation.fragment.BaseFragment
import com.eelseth.presentation.fragment.ViewModelFactory
import com.eelseth.presentation.image.ImageLoader
import com.eelseth.presentation.utils.DiffDispatcher
import com.eelseth.presentation.utils.showMessage
import com.eelseth.restaurantapp.presentation.fragment.viewBinding
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RestaurantsFragment : BaseFragment(R.layout.restaurants_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader


    private val binding by viewBinding(RestaurantsFragmentBinding::bind)
    private val viewModel: RestaurantsViewModel by viewModels { viewModelFactory }
    private var epoxyController: RestaurantsEpoxyController? = null

    private val viewModelStateDiffDispatcher by lazy {
        object : DiffDispatcher<RestaurantsViewModel.View.State>() {
            override fun onDataUpdated(
                oldData: RestaurantsViewModel.View.State?,
                newData: RestaurantsViewModel.View.State
            ) {
                if (oldData?.restaurants != newData.restaurants)
                    setDataToController(newData.restaurants)
                if (oldData?.isLoadingRequest != newData.isLoadingRequest)
                    setLoadingState(newData.isLoadingRequest)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            viewModel.onEvent(RestaurantsViewModel.View.Event.Init)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindViewModels()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStateDiffDispatcher.clearData()
    }

    private fun bindViews() {
        setupRecycler()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onEvent(RestaurantsViewModel.View.Event.Refresh)
        }
    }

    private fun bindViewModels() {
        lifecycleScope.launchWhenStarted { viewModel.state.collect { renderViewState(it) } }
        lifecycleScope.launchWhenStarted { viewModel.effect.collect { handleViewEffect(it) } }
    }


    private fun renderViewState(viewState: RestaurantsViewModel.View.State) =
        viewModelStateDiffDispatcher.setData(viewState)

    private fun setupRecycler() {
        epoxyController = RestaurantsEpoxyController(
            imageLoader = imageLoader,
            onCommand = {
                when (it) {
                    is RestaurantsEpoxyController.Command.OnRestaurantSelected ->
                        viewModel.onEvent(RestaurantsViewModel.View.Event.RestaurantSelected(it.restaurantId))
                    is RestaurantsEpoxyController.Command.OnRestaurantSaved ->
                        viewModel.onEvent(RestaurantsViewModel.View.Event.RestaurantSaved(it.restaurantId))
                }
            }
        ).also { binding.recyclerView.setController(it) }
    }

    private fun setDataToController(data: List<Restaurant>) {
        epoxyController?.setData(data)
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun handleViewEffect(viewEffect: RestaurantsViewModel.View.Effect) {
        when (viewEffect) {
            is RestaurantsViewModel.View.Effect.ShowMessage -> {
                showMessage(binding.root, viewEffect.message, viewEffect.status)
            }
            is RestaurantsViewModel.View.Effect.GoToRestaurantDetail -> {
                Toast.makeText(
                    context, "Go to detail not implemented yet", Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}