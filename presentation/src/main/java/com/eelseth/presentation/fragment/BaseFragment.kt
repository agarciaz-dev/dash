package com.eelseth.presentation.fragment

import android.app.Activity
import android.content.Context
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import toothpick.Toothpick

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    fun findMainNavController(@IdRes navigationId: Int): NavController =
        Navigation.findNavController(requireActivity(), navigationId)

    override fun onAttach(context: Context) {
        val currentActivity: Activity? = activity
        if (currentActivity != null) {
            val application = currentActivity.application
            if (application != null) {
                Toothpick.inject(this, Toothpick.openScopes(application, currentActivity, this))
            }
        }
        super.onAttach(context)
    }

    override fun onDetach() {
        Toothpick.closeScope(this)
        super.onDetach()
    }
}