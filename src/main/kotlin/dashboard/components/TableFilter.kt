package dashboard.components

import dashboard.l10n.L10n
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label

external interface TableFilterStateProps : Props {
    var filter: String
    var groupByCountry: Boolean
    var translation: L10n
}

external interface TableFilterDispatchProps : Props {
    var onChangeFilter: (String) -> Unit
    var onToggleGroupByCountry: () -> Unit
}

external interface TableFilterProps : TableFilterStateProps, TableFilterDispatchProps

class TableFilter(props: TableFilterProps) : RComponent<TableFilterProps, State>(props) {

    override fun RBuilder.render() {
        form {
            attrs.className = "form-inline mb-2 mt-2"
            div {
                attrs.className = "form-group mr-2"
                input {
                    attrs {
                        type = InputType.search
                        className = "form-control"
                        value = props.filter
                        placeholder = props.translation.searchCountry
                        onChange = { event ->
                            props.onChangeFilter(event.target.asDynamic().value as String)
                        }
                    }
                }
            }
            div {
                attrs.className = "form-group form-check"
                label {
                    input {
                        attrs {
                            type = InputType.checkbox
                            className = "form-check-input mr-2"
                            defaultChecked = props.groupByCountry
                            onChange = {
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
