package dashboard.app

import dashboard.containers.*
import react.RBuilder
import react.dom.html.ReactHTML.div

fun RBuilder.app() =
    div {
        chartFilter {}
        covidChart {}
        covidDailyChart {}
        tableFilter {}
        dataTable {}
    }
