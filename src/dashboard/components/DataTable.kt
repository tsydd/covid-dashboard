package dashboard.components

import dashboard.l10n.L10n
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
    DEATH,
    RECOVERED,
    CONFIRMED
}

data class Sort(
    val column: SortColumn,
    val order: SortOrder
)

class TableEntry(
    val selected: Boolean,
    val name: String,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int
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
                    thead {
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
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.DEATH) }
                                +props.translation.deaths
                            }
                            th {
                                attrs.onClickFunction = { props.onToggleColumn(SortColumn.RECOVERED) }
                                +props.translation.recovered
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
                                td { +entry.deaths.toString() }
                                td { +entry.recovered.toString() }
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
