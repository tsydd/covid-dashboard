package dashboard.reducers

import dashboard.l10n.L10n
import dashboard.l10n.getL10n
import redux.RAction

fun translation(state: L10n = getL10n(), action: RAction): L10n = state
