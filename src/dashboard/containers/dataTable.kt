package dashboard.containers

import dashboard.*
import dashboard.actions.ToggleSelectedKey
import dashboard.actions.ToggleSort
import dashboard.components.*
import dashboard.components.TableEntry
import dashboard.l10n.L10n
import dashboard.reducers.State
import dashboard.utils.toArray
import libs.reselect.createSelector
import react.*
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

private interface DataTableStateProps : RProps {
    var groupByCountry: Boolean
    var entries: Array<TableEntry>
    var sort: Sort
    var translation: L10n
}

private interface DataTableDispatchProps : RProps {
    var onToggleColumn: (SortColumn) -> Unit
    var onToggleRow: (String) -> Unit
}

private fun getValueByKey(
    filter: String,
    groupByCountry: Boolean,
    regionDataList: List<RegionData>
): Map<String, Int> = regionDataList.asSequence()
    .groupBy { it.getKey(groupByCountry) }
    .filterKeys { filter.isEmpty() || it.toLowerCase().contains(filter) }
    .mapValues { (_, groupedRegionData) ->
        groupedRegionData.sumBy { it.values.last() }
    }
    .toMap()

private val getEntries: (State) -> Array<TableEntry> =
    createSelector(
        { state -> state.keyFilter.toLowerCase() },
        State::covidSequences,
        State::groupByCountry,
        State::selectedKeys,
        State::sortDataTable
    ) { filter, covidData, groupByCountry, selectedCountries, sort ->
        val groupedConfirmed = getValueByKey(filter, groupByCountry, covidData.confirmed)
        val groupedDeaths = getValueByKey(filter, groupByCountry, covidData.deaths)
        val groupedRecovered = getValueByKey(filter, groupByCountry, covidData.recovered)

        sequenceOf(groupedConfirmed, groupedDeaths, groupedRecovered)
            .flatMap { it.keys.asSequence() }
            .distinct()
            .map { name ->
                TableEntry(
                    name = name,
                    confirmed = groupedConfirmed[name] ?: 0,
                    deaths = groupedDeaths[name] ?: 0,
                    recovered = groupedRecovered[name] ?: 0,
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
    }
    SortOrder.DESC -> when (sort.column) {
        SortColumn.NAME -> sortedByDescending { it.name }
        SortColumn.CONFIRMED -> sortedByDescending { it.confirmed }
        SortColumn.RECOVERED -> sortedByDescending { it.recovered }
        SortColumn.DEATH -> sortedByDescending { it.deaths }
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
