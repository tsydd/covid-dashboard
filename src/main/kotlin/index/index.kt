package index

import dashboard.actions.ResizeWindow
import dashboard.actions.UpdateCovidData
import dashboard.app.app
import dashboard.loadData
import dashboard.reducers.State
import dashboard.reducers.rootReducer
import dashboard.utils.loadState
import dashboard.utils.updateUrl
import kotlinext.js.require
import kotlinext.js.requireAll
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import react.buildElement
import react.dom.render
import react.redux.provider
import redux.Middleware
import redux.RAction
import redux.WrapperAction
import redux.applyMiddleware
import redux.compose
import redux.createStore
import redux.rEnhancer


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
    ::rootReducer, loadState(), compose(
        rEnhancer(),
        applyMiddleware(createUrlMiddleware()),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)

fun main() {
    require("bootstrap/dist/css/bootstrap.css")
    require("@fortawesome/fontawesome-free/css/solid.css")
    require("@fortawesome/fontawesome-free/css/fontawesome.css")

    requireAll(require.context(".", true, js("/\\.css$/")))

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

    val container = document.getElementById("root")!!
    val application = buildElement {
        provider(store) {
            app()
        }
    }
    render(application, container)
}
