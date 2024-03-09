package ru.akella.cryptocoin.android.core.custom

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat
import coil.ImageLoader
import com.google.accompanist.drawablepainter.rememberDrawablePainter

val LocalImageLoader: ProvidableCompositionLocal<ImageLoader?> =
    staticCompositionLocalOf { null }

@Composable
fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    @DrawableRes error: Int? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
) {
    val imageLoader = LocalImageLoader.current
    val context = LocalContext.current
    val drawable = error?.let { remember(error) { ResourcesCompat.getDrawable(context.resources, error, context.theme) } }
    val errorPainter = rememberDrawablePainter(drawable = drawable)


    if (imageLoader != null) {
        val updatedImageLoader = imageLoader.newBuilder()
            .error(drawable = drawable)
            .build()
        coil.compose.AsyncImage(
            model = model,
            contentDescription = contentDescription,
            imageLoader = updatedImageLoader,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            filterQuality = filterQuality,
        )
    } else {
        coil.compose.AsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = modifier,
            error = errorPainter,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            filterQuality = filterQuality,
        )
    }
}