package com.propertyfinder.shopr.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Shows a short toast with the given string resource and optional format args.
 */
fun Context.showToast(@StringRes resId: Int, vararg formatArgs: Any) {
    val message = if (formatArgs.isEmpty()) getString(resId) else getString(resId, *formatArgs)
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
