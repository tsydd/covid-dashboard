package dashboard.reducers

import dashboard.actions.UpdateKeyFilter
import redux.RAction

fun keyFilter(state: String = "", action: RAction): String = when (action) {
    is UpdateKeyFilter -> action.filter
    else -> state
}
