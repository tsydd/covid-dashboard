package dashboard.reducers

import dashboard.actions.UpdateAlign
import dashboard.models.Align
import redux.RAction

fun align(state: Align = Align.FIRST_CASE, action: RAction): Align = when (action) {
    is UpdateAlign -> action.align
    else -> state
}
