package dashboard.components

import dashboard.l10n.L10n
import dashboard.utils.toStringWithSign
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*

enum class SortOrder {
    ASC,
    DESC;

    operator fun not(): SortOrder =
        when (this) {
            ASC -> DESC
            DESC -> ASC
        }
}

enum class SortColumn {
    NAME,
    CONFIRMED,
    NEW_CONFIRMED,
    RECOVERED,
    NEW_RECOVERED,
    DEATHS,
    NEW_DEATHS,
    ACTIVE,
    NEW_ACTIVE,
}

data class Sort(
    val column: SortColumn,
    val order: SortOrder
)

class TableEntry(
    val selected: Boolean,
    val name: String,
    val confirmed: Int,
    val recovered: Int,
    val deaths: Int,
    val active: Int,
    val newConfirmed: Int = 0,
    val newRecovered: Int = 0,
    val newDeaths: Int = 0,
    val newActive: Int = 0
)

interface DataTableStateProps : RProps {
    var groupByCountry: Boolean
    var entries: Array<TableEntry>
    var sort: Sort
    var translation: L10n
}

interface DataTableDispatchProps : RProps {
    var onToggleColumn: (SortColumn) -> Unit
    var onToggleRow: (String) -> Unit
}

interface DataTableProps : DataTableStateProps, DataTableDispatchProps

class DataTable(props: DataTableProps) : RComponent<DataTableProps, RState>(props) {
    override fun RBuilder.render() {
        div {
            div("table-responsive covid-table-wrapper") {
                table("table table-hover") {
                    thead("thead-light") {
                        tr {
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.NAME) }
                                if (props.groupByCountry) {
                                    +props.translation.country
                                } else {
                                    +props.translation.countryRegion
                                }
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.CONFIRMED) }
                                +props.translation.confirmed
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.NEW_CONFIRMED) }
                                +props.translation.newConfirmed
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.RECOVERED) }
                                +props.translation.recovered
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.NEW_RECOVERED) }
                                +props.translation.newRecovered
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.DEATHS) }
                                +props.translation.deaths
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.NEW_DEATHS) }
                                +props.translation.newDeaths
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.ACTIVE) }
                                +props.translation.active
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.NEW_ACTIVE) }
                                +props.translation.newActive
                            }
                        }
                    }
                    tbody {
                        props.entries.forEach { entry ->
                            tr(if (entry.selected) "table-active" else null) {
                                key = entry.name
                                attrs.onClickFunction = { props.onToggleRow(entry.name) }
                                td { +entry.name }
                                td { +entry.confirmed.toString() }
                                td { +entry.newConfirmed.toStringWithSign() }
                                td { +entry.recovered.toString() }
                                td { +entry.newRecovered.toStringWithSign() }
                                td { +entry.deaths.toString() }
                                td { +entry.newDeaths.toStringWithSign() }
                                td { +entry.active.toString() }
                                td { +entry.newActive.toStringWithSign() }
                            }
                        }
                    }
                }
            }
            small("text-muted") {
                +props.translation.tableHint
            }
        }
    }
}
