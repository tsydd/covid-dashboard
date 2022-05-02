package dashboard.components

import dashboard.l10n.L10n
import dashboard.models.Align
import dashboard.models.CovidData
import dashboard.models.CovidDataType
import dashboard.utils.toStringWithSign
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.small

external interface ChartFilterStateProps : Props {
    var updated: String?
    var align: Align
    var include: CovidData<Boolean>
    var total: CovidData<Int>
    var dailyTotal: CovidData<Int>
    var translation: L10n
}

external interface ChartFilterDispatchProps : Props {
    var onToggle: (CovidDataType) -> Unit
    var onUpdateAlign: (Align) -> Unit
}

external interface ChartFilterProps : ChartFilterStateProps, ChartFilterDispatchProps

class ChartFilter(props: ChartFilterProps) : RComponent<ChartFilterProps, State>(props) {
    private fun RBuilder.renderCheckbox(dataType: CovidDataType, classSuffix: String) {
        label {
            attrs.className = "alert alert-$classSuffix mr-2"
            input {
                attrs {
                    type = InputType.checkbox
                    className = "form-check-input mr-2"
                    defaultChecked = props.include[dataType]
                    onChange = { props.onToggle(dataType) }
                }
            }
            +dataType.translate(props.translation)
            div {
                attrs.className = "badge badge-$classSuffix ml-2"
                +"${props.total[dataType]} (${props.dailyTotal[dataType].toStringWithSign()})"
            }
        }
    }

    override fun RBuilder.render() {
        form {
            attrs.className = "mt-2"
            p {
                attrs.className = "lead"
                +props.translation.title
            }
            if (props.updated != null) {
                small {
                    attrs.className = "mr-2 mb-2"
                    +"${props.translation.updated}: ${props.updated}"
                }
            }
            div {
                attrs.className = "form-inline"
                select {
                    attrs {
                        className = "custom-select mr-2 mb-2"
                        value = props.align.name
                        onChange = { event ->
                            props.onUpdateAlign(Align.valueOf(event.target.asDynamic().value as String))
                        }
                    }
                    Align.values().forEach {
                        ReactHTML.option {
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
