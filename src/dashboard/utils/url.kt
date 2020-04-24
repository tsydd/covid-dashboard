package dashboard.utils

import dashboard.actions.ToggleGroupByCountry
import dashboard.actions.ToggleSelectedDataType
import dashboard.actions.ToggleSelectedKey
import dashboard.actions.UpdateAlign
import dashboard.models.Align
import dashboard.models.CovidData
import dashboard.reducers.State
import org.w3c.dom.url.URL
import org.w3c.dom.url.URLSearchParams
import redux.RAction
import kotlin.browser.window

fun URLSearchParams.getBoolean(property: String, default: Boolean): Boolean = get(property)?.toBoolean() ?: default

fun URLSearchParams.getSet(property: String): Set<String> = get(property)
    ?.splitToSequence(";")
    ?.filter { it.isNotBlank() }
    ?.toSet()
    ?: emptySet()

fun loadState(): State {
    val params = URL(window.location.href).searchParams
    val align = params.get("align")
    val confirmed = params.getBoolean("confirmed", true)
    val recovered = params.getBoolean("recovered", true)
    val deaths = params.getBoolean("deaths", true)
    val active = params.getBoolean("active", true)

    return State(
        groupByCountry = params.getBoolean("groupByCountry", true),
        selectedKeys = params.getSet("keys"),
        align = Align.values().firstOrNull { it.name == align } ?: Align.FIRST_CASE,
        selectedDataTypes = CovidData(
            confirmed = confirmed,
            recovered = recovered,
            deaths = deaths,
            active = active
        )
    )
}

fun updateUrl(state: State, action: RAction) {
    when (action) {
        is ToggleGroupByCountry,
        is ToggleSelectedKey,
        is UpdateAlign,
        is ToggleSelectedDataType -> {
            val params = URLSearchParams()
            params.set("groupByCountry", state.groupByCountry.toString())
            if (state.selectedKeys.isNotEmpty()) {
                params.set("keys", state.selectedKeys.joinToString(";"))
            }
            params.set("align", state.align.name)
            params.set("confirmed", state.selectedDataTypes.confirmed.toString())
            params.set("recovered", state.selectedDataTypes.recovered.toString())
            params.set("deaths", state.selectedDataTypes.deaths.toString())
            params.set("active", state.selectedDataTypes.active.toString())

            window.history.pushState(null, "hello", "?$params")
            eval("gtag('config', 'UA-34660136-2', {'page_path': '/covid-19/?$params'})")
        }
    }
}
