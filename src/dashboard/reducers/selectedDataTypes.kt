package dashboard.reducers

import dashboard.models.CovidData
import dashboard.actions.ToggleSelectedDataType
import redux.RAction

fun selectedDataTypes(state: CovidData<Boolean> = CovidData(
    true
), action: RAction): CovidData<Boolean> =
    when (action) {
        is ToggleSelectedDataType -> state.with(action.dataType, !state[action.dataType])
        else -> state
    }
