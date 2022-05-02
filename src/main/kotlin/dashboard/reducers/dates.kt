package dashboard.reducers

import dashboard.actions.UpdateCovidData
import redux.RAction

fun dates(state: List<String> = emptyList(), action: RAction): List<String> = when (action) {
    is UpdateCovidData -> action.covidData.dates
    else -> state
}
