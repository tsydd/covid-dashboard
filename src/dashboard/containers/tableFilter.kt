package dashboard.containers

import dashboard.L10n
import dashboard.reducers.State
import dashboard.actions.UpdateKeyFilter
import dashboard.actions.ToggleGroupByCountry
import dashboard.components.TableFilter
import dashboard.components.TableFilterProps
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

private interface TableFilterStateProps : RProps {
    var filter: String
    var groupByCountry: Boolean
    var translation: L10n
}

private interface TableFilterDispatchProps : RProps {
    var onChangeFilter: (String) -> Unit
    var onToggleGroupByCountry: () -> Unit
}

val tableFilter: RClass<RProps> =
    rConnect<State, RAction, WrapperAction, RProps, TableFilterStateProps, TableFilterDispatchProps, TableFilterProps>(
        { state, _ ->
            filter = state.keyFilter
            groupByCountry = state.groupByCountry
            translation = state.translation
        },
        { dispatch, _ ->
            onChangeFilter = { newFilter ->
                dispatch(UpdateKeyFilter(newFilter))
            }
            onToggleGroupByCountry = {
                dispatch(ToggleGroupByCountry)
            }
        }
    )(TableFilter::class.js.unsafeCast<RClass<TableFilterProps>>())
