package dashboard.components

import dashboard.L10n
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import react.*
import react.dom.div
import react.dom.form
import react.dom.input
import react.dom.label

interface TableFilterProps : RProps {
    var filter: String
    var onChangeFilter: (String) -> Unit
    var groupByCountry: Boolean
    var onToggleGroupByCountry: () -> Unit
    var translation: L10n
}

class TableFilter(props: TableFilterProps) : RComponent<TableFilterProps, RState>(props) {

    override fun RBuilder.render() {
        form(classes = "form-inline mb-2") {
            div("form-group mr-2") {
                input(type = InputType.search, classes = "form-control") {
                    attrs {
                        value = props.filter
                        placeholder = props.translation.searchCountry
                        onChangeFunction = { event ->
                            props.onChangeFilter(event.target!!.asDynamic().value as String)
                        }
                    }
                }
            }
            div("form-group form-check") {
                label {
                    input(type = InputType.checkBox, classes = "form-check-input mr-2") {
                        attrs {
                            defaultChecked = props.groupByCountry
                            onChangeFunction = {
                                props.onToggleGroupByCountry()
                            }
                        }
                    }
                    +props.translation.groupByCountry
                }
            }
        }
    }
}
