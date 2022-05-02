package dashboard.components

import dashboard.l10n.L10n
import dashboard.utils.toStringWithSign
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.small
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.thead
import react.dom.html.ReactHTML.tr

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

external interface DataTableStateProps : Props {
    var groupByCountry: Boolean
    var entries: Array<TableEntry>
    var sort: Sort
    var translation: L10n
}

external interface DataTableDispatchProps : Props {
    var onToggleColumn: (SortColumn) -> Unit
    var onToggleRow: (String) -> Unit
}

external interface DataTableProps : DataTableStateProps, DataTableDispatchProps

class DataTable(props: DataTableProps) : RComponent<DataTableProps, State>(props) {

    private fun RBuilder.renderSortIcon(sortColumn: SortColumn) {
        if (props.sort.column == sortColumn) {
            span {
                attrs.className = "ml-2"
                i {
                    attrs.className = when (props.sort.order) {
                        SortOrder.ASC -> "fas fa-sort-up"
                        SortOrder.DESC -> "fas fa-sort-down"
                    }
                }
            }
        } else {
            span {
                attrs.className = "text-muted ml-2"
                i {
                    attrs.className = "fas fa-sort"
                }
            }
        }
    }

    override fun RBuilder.render() {
        div {
            div {
                attrs.className = "table-responsive covid-table-wrapper"
                table {
                    attrs.className = "table table-hover"
                    thead {
                        attrs.className = "thead-light"
                        tr {
                            th {
                                +"#"
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.NAME) }
                                if (props.groupByCountry) {
                                    +props.translation.country
                                } else {
                                    +props.translation.countryRegion
                                }
                                renderSortIcon(SortColumn.NAME)
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.CONFIRMED) }
                                +props.translation.confirmed
                                renderSortIcon(SortColumn.CONFIRMED)
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.NEW_CONFIRMED) }
                                +props.translation.newConfirmed
                                renderSortIcon(SortColumn.NEW_CONFIRMED)
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.RECOVERED) }
                                +props.translation.recovered
                                renderSortIcon(SortColumn.RECOVERED)
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.NEW_RECOVERED) }
                                +props.translation.newRecovered
                                renderSortIcon(SortColumn.NEW_RECOVERED)
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.DEATHS) }
                                +props.translation.deaths
                                renderSortIcon(SortColumn.DEATHS)
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.NEW_DEATHS) }
                                +props.translation.newDeaths
                                renderSortIcon(SortColumn.NEW_DEATHS)
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.ACTIVE) }
                                +props.translation.active
                                renderSortIcon(SortColumn.ACTIVE)
                            }
                            th {
                                attrs.onClick = { props.onToggleColumn(SortColumn.NEW_ACTIVE) }
                                +props.translation.newActive
                                renderSortIcon(SortColumn.NEW_ACTIVE)
                            }
                        }
                    }
                    tbody {
                        props.entries.forEachIndexed { index, entry ->
                            tr {
                                if (entry.selected) {
                                    attrs.className = "table-active"
                                }
                                key = entry.name
                                attrs.onClick = { props.onToggleRow(entry.name) }
                                td { +(index + 1).toString() }
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
            small {
                attrs.className = "text-muted"
                +props.translation.tableHint
            }
        }
    }
}
