package dashboard.components

import dashboard.l10n.L10n
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*

interface NavBarStateProps : RProps {
    var updated: String?
    var translation: L10n
}

interface NavBarProps : NavBarStateProps

class NavBar(props: NavBarProps) : RComponent<NavBarProps, RState>(props) {
    override fun RBuilder.render() {
        nav("navbar navbar-expand navbar-dark bg-dark justify-content-between") {
            span("navbar-brand") {
                +"COVID-19 Dashboard - charts and statistics. Compare and analyse multiple countries on a single chart"
            }
            div("navbar-collapse collapse") {
                ul("navbar-nav ml-auto") {
                    if (props.updated != null) {
                        li("nav-item") {
                            span("nav-link active") {
                                +"${props.translation.updated}: ${props.updated}"
                            }
                        }
                    }
                }
            }
        }
    }
}
