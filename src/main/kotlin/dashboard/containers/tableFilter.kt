package dashboard.containers

import dashboard.actions.ToggleGroupByCountry
import dashboard.actions.UpdateKeyFilter
import dashboard.components.TableFilter
import dashboard.components.TableFilterDispatchProps
import dashboard.components.TableFilterProps
import dashboard.components.TableFilterStateProps
import dashboard.reducers.State
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

val tableFilter: ComponentClass<Props> =
    rConnect<State, RAction, WrapperAction, Props, TableFilterStateProps, TableFilterDispatchProps, TableFilterProps>(
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
    )(TableFilter::class.js.unsafeCast<ComponentClass<TableFilterProps>>())
