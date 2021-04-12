package com.eelseth.dash

import android.app.Application
import com.eelseth.dash.di.AppModule
import com.eelseth.domain.di.DomainModule
import com.eelseth.network.di.NetworkModule
import com.eelseth.persistence.di.PersistenceModule
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.ktp.KTP
import toothpick.smoothie.module.SmoothieApplicationModule


class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setToothpickConfig()
        setupDIGraph()
    }

    private fun setToothpickConfig() {
        val configuration = if (BuildConfig.BUILD_TYPE == "debug") {
            Configuration.forDevelopment()
        } else {
            Configuration.forProduction()
        }
        Toothpick.setConfiguration(configuration.preventMultipleRootScopes())
    }

    private fun setupDIGraph() {
        KTP.openScope(applicationContext)
            .installModules(
                SmoothieApplicationModule(this),
                AppModule(this),
                NetworkModule(),
                PersistenceModule(this),
                DomainModule()
            ).inject(this)
    }
}