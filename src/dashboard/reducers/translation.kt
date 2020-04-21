package dashboard.reducers

import dashboard.L10n
import dashboard.getL10n
import redux.RAction

fun translation(state: L10n = getL10n(), action: RAction): L10n = when (action) {
    else -> state
}
