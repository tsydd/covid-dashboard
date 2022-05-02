package dashboard.reducers

import dashboard.RegionData
import dashboard.components.Sort
import dashboard.components.SortColumn
import dashboard.components.SortOrder
import dashboard.l10n.L10n
import dashboard.l10n.getL10n
import dashboard.models.Align
import dashboard.models.CovidData
import kotlinx.browser.window
import redux.RAction

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
    val windowSize: Size = Size(window.innerWidth, window.innerHeight),
    val translation: L10n = getL10n()
)

fun rootReducer(
    state: State,
    action: Any
) = State(
    keyFilter(state.keyFilter, action.unsafeCast<RAction>()),
    groupByCountry(state.groupByCountry, action.unsafeCast<RAction>()),
    dates(state.dates, action.unsafeCast<RAction>()),
    covidSequences(state.covidSequences, action.unsafeCast<RAction>()),
    sortDataTable(state.sortDataTable, action.unsafeCast<RAction>()),
    selectedKeys(state.selectedKeys, action.unsafeCast<RAction>()),
    selectedDataTypes(state.selectedDataTypes, action.unsafeCast<RAction>()),
    align(state.align, action.unsafeCast<RAction>()),
    windowSize(state.windowSize, action.unsafeCast<RAction>()),
    translation(state.translation, action.unsafeCast<RAction>())
)
