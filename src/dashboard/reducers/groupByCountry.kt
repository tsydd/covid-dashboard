package dashboard.reducers

import dashboard.actions.ToggleGroupByCountry
import redux.RAction

fun groupByCountry(state: Boolean = true, action: RAction): Boolean = when (action) {
    is ToggleGroupByCountry -> !state
    else -> state
}
