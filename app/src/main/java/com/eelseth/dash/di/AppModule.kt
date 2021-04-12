package com.eelseth.dash.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.eelseth.core.dispatcher.DispatcherProvider
import com.eelseth.presentation.fragment.ViewModelFactory
import com.eelseth.presentation.image.GlideImageLoader
import com.eelseth.presentation.image.ImageLoader
import com.eelseth.restaurantapp.core.dispatcher.DispatcherProviderImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule(context: Context) : Module() {
    init {
        bind<Context>().toInstance(context)
        bind<DispatcherProvider>().toClass<DispatcherProviderImpl>()
        bind<ImageLoader>().toClass<GlideImageLoader>()
        bind<ViewModelProvider.Factory>().toClass<ViewModelFactory>()
    }
}