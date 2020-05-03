package dashboard.components

import dashboard.l10n.L10n
import dashboard.models.Align
import dashboard.models.CovidData
import dashboard.models.CovidDataType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*

interface ChartFilterStateProps : RProps {
    var updated: String?
    var align: Align
    var include: CovidData<Boolean>
    var total: CovidData<Int>
    var translation: L10n
}

interface ChartFilterDispatchProps : RProps {
    var onToggle: (CovidDataType) -> Unit
    var onUpdateAlign: (Align) -> Unit
}

interface ChartFilterProps : ChartFilterStateProps, ChartFilterDispatchProps

class ChartFilter(props: ChartFilterProps) : RComponent<ChartFilterProps, RState>(props) {
    private fun RBuilder.renderCheckbox(type: CovidDataType, classSuffix: String) {
        label("alert alert-$classSuffix mr-2") {
            input(type = InputType.checkBox, classes = "form-check-input mr-2") {
                attrs.defaultChecked = props.include[type]
                attrs.onChangeFunction = { props.onToggle(type) }
            }
            +type.translate(props.translation)
            div("badge badge-$classSuffix ml-2") {
                +"${props.total[type]}"
            }
        }
    }

    override fun RBuilder.render() {
        form(classes = "mt-2") {
            div("form-inline mb-2") {
                if (props.updated != null) {
                    div("mr-2") {
                        +"${props.translation.updated} ${props.updated}."
                    }
                }
                label("mr-2") {
                    div("mr-2") {
                        +props.translation.align
                    }
                    select("custom-select") {
                        attrs.value = props.align.name
                        attrs.onChangeFunction = { event ->
                            props.onUpdateAlign(Align.valueOf(event.target!!.asDynamic().value as String))
                        }
                        Align.values().forEach {
                            option {
                                attrs.value = it.name
                                +it.translate(props.translation)
                            }
                        }
                    }
                }
            }
            div("form-inline") {
                renderCheckbox(CovidDataType.CONFIRMED, classSuffix = "warning")
                renderCheckbox(CovidDataType.RECOVERED, classSuffix = "success")
                renderCheckbox(CovidDataType.DEATHS, classSuffix = "danger")
                renderCheckbox(CovidDataType.ACTIVE, classSuffix = "info")
            }
        }
    }
}
