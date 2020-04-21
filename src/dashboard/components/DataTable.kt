package dashboard.components

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

interface DataTableProps : RProps {
    var groupByCountry: Boolean
    var entries: Array<TableEntry>
    var sort: Sort
    var onToggleColumn: (SortColumn) -> Unit
    var onToggleRow: (String) -> Unit
}

class DataTable(props: DataTableProps) : RComponent<DataTableProps, RState>(props) {
    override fun RBuilder.render() {
        div("covid-table-wrapper") {
            table("table table-hover") {
                thead {
                    tr {
                        th {
                            attrs.onClickFunction = { props.onToggleColumn(SortColumn.NAME) }
                            if (props.groupByCountry) {
                                +"Country"
                            } else {
                                +"Country/Region"
                            }
                        }
                        th {
                            attrs.onClickFunction = { props.onToggleColumn(SortColumn.CONFIRMED) }
                            +"Confirmed"
                        }
                        th {
                            attrs.onClickFunction = { props.onToggleColumn(SortColumn.DEATH) }
                            +"Deaths"
                        }
                        th {
                            attrs.onClickFunction = { props.onToggleColumn(SortColumn.RECOVERED) }
                            +"Recovered"
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
    }
}
