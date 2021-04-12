package com.eelseth.dash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import toothpick.Toothpick

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toothpick.inject(this, Toothpick.openScopes(application, this))
    }
}