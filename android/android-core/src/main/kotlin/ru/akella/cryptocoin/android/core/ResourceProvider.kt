package ru.akella.cryptocoin.android.core

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getColorOrThrow

class ResourceProvider(
    private val context: Context
) {

    fun getString(@StringRes id: Int, vararg formatArgs: Any)
        = context.resources.getString(id, *formatArgs)

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any)
        = context.resources.getQuantityString(id, quantity, quantity, *formatArgs)

    fun getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(context, colorRes)

    fun getDimension(@DimenRes dimenRes: Int) = context.resources.getDimension(dimenRes)

    fun getTypeface(@FontRes fontRes: Int) = ResourcesCompat.getFont(context, fontRes) ?: throw RuntimeException()

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable = context.resources.getDrawable(drawableRes, context.theme)

    fun getColorsArray(@ArrayRes arrayRes: Int): IntArray {
        return context
            .resources
            .obtainTypedArray(arrayRes)
            .let { array ->
                val result = IntArray(array.length())
                for (index in 0 until array.length()) {
                    result[index] = array.getColorOrThrow(index)
                }
                result
            }
    }

    fun getDisplayMetrics(): DisplayMetrics = context.resources.displayMetrics

    fun getInteger(@IntegerRes intRes: Int) = context.resources.getInteger(intRes)

    fun getFloat(@DimenRes floatRes: Int) = ResourcesCompat.getFloat(context.resources, floatRes)

    fun getSystemService(service: String): Any = context.getSystemService(service)
}