package dashboard.selectors

import dashboard.reducers.State
import libs.reselect.createSelector

val aspectRatio: (State) -> Int =
    createSelector(
        State::windowSize
    ) { (width, _) ->
        when (width) {
            in (0..450) -> 1
            in (450..700) -> 2
            in (700..1000) -> 3
            in (1000..1500) -> 4
            in (1500..2000) -> 5
            in (1500..2500) -> 6
            else -> 7
        }
    }
