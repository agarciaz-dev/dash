package com.eelseth.presentation.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.eelseth.presentation.R
import com.eelseth.presentation.model.MessageStatus
import com.google.android.material.snackbar.Snackbar

fun showMessage(view: View, @StringRes message: Int, status: MessageStatus) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    when (status) {
        MessageStatus.Success -> snackBar.withColor(
            R.color.colorUIPositive,
            R.color.colorNeutral100
        )
        MessageStatus.Error -> snackBar.withColor(
            R.color.colorUINegative,
            R.color.colorNeutral100
        )
    }.show()
}

private fun Snackbar.withColor(@ColorRes backgroundColor: Int, @ColorRes textColor: Int): Snackbar {
    this.view.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        .setTextColor(ContextCompat.getColor(context, textColor))
    return this
}