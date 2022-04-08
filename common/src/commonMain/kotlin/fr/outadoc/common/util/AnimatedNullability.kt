package fr.outadoc.common.util

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T : Any> AnimatedNullability(
    param: T?,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    content: @Composable AnimatedVisibilityScope.(T) -> Unit
) {
    AnimatedVisibility(
        visible = param != null,
        modifier = modifier,
        enter = enter,
        exit = exit,
        content = {
            param?.let { content(param) }
        }
    )
}
