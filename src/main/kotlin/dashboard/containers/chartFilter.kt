package dashboard.containers

import dashboard.actions.ToggleSelectedDataType
import dashboard.actions.UpdateAlign
import dashboard.components.ChartFilter
import dashboard.components.ChartFilterDispatchProps
import dashboard.components.ChartFilterProps
import dashboard.components.ChartFilterStateProps
import dashboard.models.CovidData
import dashboard.reducers.State
import dashboard.selectors.dailySelectedSequences
import dashboard.selectors.selectedSequences
import libs.reselect.createSelector
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

private fun sumLastValues(sequences: CovidData<Map<String, List<Int>>>): CovidData<Int> = sequences
    .map { sequenceByKey ->
        sequenceByKey.values.sumOf { it.lastOrNull() ?: 0 }
    }

private val selectedTotal: (State) -> CovidData<Int> =
    createSelector(
        { state: State -> selectedSequences(state) },
        ::sumLastValues
    )

private val selectedDailyTotal: (State) -> CovidData<Int> =
    createSelector(
        { state: State -> dailySelectedSequences(state) },
        ::sumLastValues
    )

val chartFilter: ComponentClass<ChartFilterStateProps> =
    rConnect<State, RAction, WrapperAction, Props, ChartFilterStateProps, ChartFilterDispatchProps, ChartFilterProps>(
        { state, _ ->
            updated = state.dates.lastOrNull()
            align = state.align
            include = state.selectedDataTypes
            total = selectedTotal(state)
            dailyTotal = selectedDailyTotal(state)
            translation = state.translation
        },
        { dispatch, _ ->
            onToggle = { series -> dispatch(ToggleSelectedDataType(series)) }
            onUpdateAlign = { align -> dispatch(UpdateAlign(align)) }
        }
    )(ChartFilter::class.js.unsafeCast<ComponentClass<ChartFilterProps>>())
