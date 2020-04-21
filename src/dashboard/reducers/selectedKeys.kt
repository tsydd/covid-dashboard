package dashboard.reducers

import dashboard.actions.ToggleGroupByCountry
import dashboard.actions.ToggleSelectedKey
import redux.RAction

fun selectedKeys(state: Set<String> = emptySet(), action: RAction): Set<String> = when (action) {
    is ToggleGroupByCountry -> emptySet()
    is ToggleSelectedKey -> {
        if (action.key in state) {
            state - action.key
        } else {
            state + action.key
        }
    }
    else -> state
}
