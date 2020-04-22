package index

import dashboard.actions.ResizeWindow
import dashboard.actions.UpdateCovidData
import dashboard.app.app
import dashboard.loadData
import dashboard.utils.loadState
import dashboard.reducers.State
import dashboard.reducers.combinedReducers
import dashboard.utils.updateUrl
import kotlinext.js.require
import kotlinext.js.requireAll
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import react.dom.render
import react.redux.provider
import redux.*
import kotlin.browser.document
import kotlin.browser.window


fun <A, R> createUrlMiddleware(): Middleware<State, A, R, A, R> =
    { store ->
        { next: (A) -> R ->
            { action: A ->
                val result = next(action)
                updateUrl(
                    store.getState(),
                    action.unsafeCast<WrapperAction>().action
                )
                result
            }
        }
    }

val store = createStore<State, RAction, dynamic>(
    combinedReducers(), loadState(), compose(
        rEnhancer(),
        applyMiddleware(createUrlMiddleware()),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)

fun main() {
    require("bootstrap/dist/css/bootstrap.css")
    requireAll(require.context("src", true, js("/\\.css$/")))

    window.addEventListener("resize", callback = {
        store.dispatch(ResizeWindow(
            width = window.innerWidth,
            height = window.innerHeight
        ))
        Unit
    })

    GlobalScope.launch {
        val covidData = loadData()
        store.dispatch(UpdateCovidData(covidData))
        Unit
    }

    render(document.getElementById("root")) {
        provider(store) {
            app()
        }
    }
}
