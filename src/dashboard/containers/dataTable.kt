package dashboard.containers

import dashboard.actions.ToggleSelectedKey
import dashboard.actions.ToggleSort
import dashboard.components.*
import dashboard.reducers.State
import dashboard.selectors.dailyLastValues
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
        { state -> dailyLastValues(state) },
        State::groupByCountry,
        State::selectedKeys,
        State::sortDataTable
    ) { filter, groupedLastValues, dailyGroupedLastValues, groupByCountry, selectedCountries, sort ->
        val lastValues = groupedLastValues[groupByCountry]
        val dailyLastValues = dailyGroupedLastValues[groupByCountry]

        lastValues.confirmed.keys.asSequence()
            .filter { filter.isEmpty() || it.toLowerCase().contains(filter) }
            .distinct()
            .map { name ->
                TableEntry(
                    name = name,
                    confirmed = lastValues.confirmed[name] ?: 0,
                    newConfirmed = dailyLastValues.confirmed[name] ?: 0,
                    recovered = lastValues.recovered[name] ?: 0,
                    newRecovered = dailyLastValues.recovered[name] ?: 0,
                    deaths = lastValues.deaths[name] ?: 0,
                    newDeaths = dailyLastValues.deaths[name] ?: 0,
                    active = lastValues.active[name] ?: 0,
                    newActive = dailyLastValues.active[name] ?: 0,
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
        SortColumn.NEW_CONFIRMED -> sortedBy { it.newConfirmed }
        SortColumn.RECOVERED -> sortedBy { it.recovered }
        SortColumn.NEW_RECOVERED -> sortedBy { it.newRecovered }
        SortColumn.DEATHS -> sortedBy { it.deaths }
        SortColumn.NEW_DEATHS -> sortedBy { it.newDeaths }
        SortColumn.ACTIVE -> sortedBy { it.active }
        SortColumn.NEW_ACTIVE -> sortedBy { it.newActive }
   }
    SortOrder.DESC -> when (sort.column) {
        SortColumn.NAME -> sortedByDescending { it.name }
        SortColumn.CONFIRMED -> sortedByDescending { it.confirmed }
        SortColumn.NEW_CONFIRMED -> sortedByDescending { it.newConfirmed }
        SortColumn.RECOVERED -> sortedByDescending { it.recovered }
        SortColumn.NEW_RECOVERED -> sortedByDescending { it.newRecovered }
        SortColumn.DEATHS -> sortedByDescending { it.deaths }
        SortColumn.NEW_DEATHS -> sortedByDescending { it.newDeaths }
        SortColumn.ACTIVE -> sortedByDescending { it.active }
        SortColumn.NEW_ACTIVE -> sortedByDescending { it.newActive }
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
