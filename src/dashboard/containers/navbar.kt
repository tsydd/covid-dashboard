package dashboard.containers

import dashboard.components.NavBar
import dashboard.components.NavBarProps
import dashboard.components.NavBarStateProps
import dashboard.reducers.State
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

val navBar: RClass<RProps> =
    rConnect<State, RAction, WrapperAction, RProps, NavBarStateProps, RProps, NavBarProps>(
        { state, _ ->
            updated = state.dates.lastOrNull()
            translation = state.translation
        },
        { dispatch, _ -> }
    )(NavBar::class.js.unsafeCast<RClass<NavBarProps>>())
