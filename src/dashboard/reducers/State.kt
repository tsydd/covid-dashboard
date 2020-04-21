package dashboard.reducers

import dashboard.models.CovidData
import dashboard.RegionData
import dashboard.components.Sort
import dashboard.components.SortColumn
import dashboard.components.SortOrder
import dashboard.models.Align
import redux.RAction
import redux.combineReducers
import kotlin.browser.window

data class State(
    val keyFilter: String = "",
    val groupByCountry: Boolean = true,
    val dates: List<String> = emptyList(),
    val covidSequences: CovidData<List<RegionData>> = CovidData(
        emptyList()
    ),
    val sortDataTable: Sort = Sort(
        column = SortColumn.NAME,
        order = SortOrder.ASC
    ),
    val selectedKeys: Set<String> = emptySet(),
    val selectedDataTypes: CovidData<Boolean> = CovidData(
        true
    ),
    val align: Align = Align.FIRST_CASE,
    val windowSize: Size = Size(window.innerWidth, window.innerHeight)
)

fun combinedReducers() = combineReducers<State, RAction>(
    mapOf(
        "keyFilter" to ::keyFilter,
        "groupByCountry" to ::groupByCountry,
        "dates" to ::dates,
        "covidSequences" to ::covidSequences,
        "sortDataTable" to ::sortDataTable,
        "selectedKeys" to ::selectedKeys,
        "selectedDataTypes" to ::selectedDataTypes,
        "align" to ::align,
        "windowSize" to ::windowSize
    )
)
