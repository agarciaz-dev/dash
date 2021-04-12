package com.eelseth.restaurantapp.core.dispatcher

import com.eelseth.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import toothpick.InjectConstructor

@InjectConstructor
class DispatcherProviderImpl : DispatcherProvider {

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}