package dashboard.containers

import dashboard.actions.ToggleSelectedKey
import dashboard.actions.ToggleSort
import dashboard.components.*
import dashboard.reducers.State
import dashboard.selectors.lastValues
import dashboard.utils.toArray
import libs.reselect.createSelector
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

private val getEntries: (State) -> Array<TableEntry> =
    createSelector(
        { state -> state.keyFilter.toLowerCase() },
        { state -> lastValues(state) },
        State::groupByCountry,
        State::selectedKeys,
        State::sortDataTable
    ) { filter, groupedLastValues, groupByCountry, selectedCountries, sort ->
        val lastValues = groupedLastValues[groupByCountry]

        lastValues.confirmed.keys.asSequence()
            .filter { filter.isEmpty() || it.toLowerCase().contains(filter) }
            .distinct()
            .map { name ->
                TableEntry(
                    name = name,
                    confirmed = lastValues.confirmed[name] ?: 0,
                    recovered = lastValues.recovered[name] ?: 0,
                    deaths = lastValues.deaths[name] ?: 0,
                    active = lastValues.active[name] ?: 0,
                    selected = name in selectedCountries
                )
            }
            .sort(sort)
            .toArray()
    }

fun Sequence<TableEntry>.sort(sort: Sort): Sequence<TableEntry> = when (sort.order) {
    SortOrder.ASC -> when (sort.column) {
        SortColumn.NAME -> sortedBy { it.name }
        SortColumn.CONFIRMED -> sortedBy { it.confirmed }
        SortColumn.RECOVERED -> sortedBy { it.recovered }
        SortColumn.DEATH -> sortedBy { it.deaths }
        SortColumn.ACTIVE -> sortedBy { it.active }
    }
    SortOrder.DESC -> when (sort.column) {
        SortColumn.NAME -> sortedByDescending { it.name }
        SortColumn.CONFIRMED -> sortedByDescending { it.confirmed }
        SortColumn.RECOVERED -> sortedByDescending { it.recovered }
        SortColumn.DEATH -> sortedByDescending { it.deaths }
        SortColumn.ACTIVE -> sortedByDescending { it.active }
    }
}

val dataTable: RClass<RProps> =
    rConnect<State, RAction, WrapperAction, RProps, DataTableStateProps, DataTableDispatchProps, DataTableProps>(
        { state, _ ->
            groupByCountry = state.groupByCountry
            entries = getEntries(state)
            sort = state.sortDataTable
            translation = state.translation
        },
        { dispatch, _ ->
            onToggleColumn = { columnName -> dispatch(ToggleSort(columnName)) }
            onToggleRow = { country -> dispatch(ToggleSelectedKey(country)) }
        }
    )(DataTable::class.js.unsafeCast<RClass<DataTableProps>>())
