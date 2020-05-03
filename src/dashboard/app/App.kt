package dashboard.app

import dashboard.containers.*
import react.RBuilder
import react.dom.div

fun RBuilder.app() =
    div {
        chartFilter {}
        covidChart {}
        covidDailyChart {}
        tableFilter {}
        dataTable {}
    }
