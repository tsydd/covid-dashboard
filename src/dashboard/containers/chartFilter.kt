package dashboard.containers

import dashboard.actions.ToggleSelectedDataType
import dashboard.actions.UpdateAlign
import dashboard.components.ChartFilter
import dashboard.components.ChartFilterDispatchProps
import dashboard.components.ChartFilterProps
import dashboard.components.ChartFilterStateProps
import dashboard.models.CovidData
import dashboard.reducers.State
import dashboard.selectors.selectedSequences
import libs.reselect.createSelector
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

val selectedSeriesSums: (State) -> CovidData<Int> =
    createSelector (
        { state: State -> selectedSequences(state) }
    ) { sequences ->
        sequences.map { sequenceByKey ->
            sequenceByKey.values.sumBy { it.lastOrNull() ?: 0 }
        }
    }

val chartFilter: RClass<RProps> =
    rConnect<State, RAction, WrapperAction, RProps, ChartFilterStateProps, ChartFilterDispatchProps, ChartFilterProps>(
        { state, _ ->
            align = state.align
            include = state.selectedDataTypes
            total = selectedSeriesSums(state)
            translation = state.translation
        },
        { dispatch, _ ->
            onToggle = { series -> dispatch(ToggleSelectedDataType(series)) }
            onUpdateAlign = { align -> dispatch(UpdateAlign(align)) }
        }
    )(ChartFilter::class.js.unsafeCast<RClass<ChartFilterProps>>())
