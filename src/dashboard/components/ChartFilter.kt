package dashboard.components

import dashboard.l10n.L10n
import dashboard.models.Align
import dashboard.models.CovidData
import dashboard.models.CovidDataType
import dashboard.utils.toStringWithSign
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
    var dailyTotal: CovidData<Int>
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
                +"${props.total[type]} (${props.dailyTotal[type].toStringWithSign()})"
            }
        }
    }

    override fun RBuilder.render() {
        form(classes = "mt-2") {
            p("lead") {
                +props.translation.title
            }
            if (props.updated != null) {
                small("mr-2 mb-2") {
                    +"${props.translation.updated}: ${props.updated}"
                }
            }
            div("form-inline") {
                select("custom-select mr-2 mb-2") {
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
                renderCheckbox(CovidDataType.CONFIRMED, classSuffix = "warning")
                renderCheckbox(CovidDataType.RECOVERED, classSuffix = "success")
                renderCheckbox(CovidDataType.DEATHS, classSuffix = "danger")
                renderCheckbox(CovidDataType.ACTIVE, classSuffix = "info")
            }
        }
    }
}
