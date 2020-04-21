package dashboard.reducers

import dashboard.actions.ResizeWindow
import redux.RAction
import kotlin.browser.window

class Size(
    val width: Int,
    val height: Int
)

fun windowSize(state: Size = Size(window.innerWidth, window.innerHeight), action: RAction): Size = when (action) {
    is ResizeWindow -> Size(
        width = action.width,
        height = action.height
    )
    else -> state
}
