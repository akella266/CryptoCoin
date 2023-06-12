package ru.akella.cryptocoin.android.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import ru.akella.cryptocoin.android.ui.main.LocalImageLoader

@Composable
fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    transform: (AsyncImagePainter.State) -> AsyncImagePainter.State = AsyncImagePainter.DefaultTransform,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
) {
    val imageLoader = LocalImageLoader.current
    if (imageLoader != null) {
        coil.compose.AsyncImage(
            model,
            contentDescription,
            imageLoader,
            modifier,
            transform,
            onState,
            alignment,
            contentScale,
            alpha,
            colorFilter,
            filterQuality,
        )
    } else {
        coil.compose.AsyncImage(
            model,
            contentDescription,
            modifier,
            transform,
            onState,
            alignment,
            contentScale,
            alpha,
            colorFilter,
            filterQuality,
        )
    }
}