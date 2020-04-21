package dashboard.containers

import dashboard.components.CovidChart
import dashboard.components.CovidChartProps
import dashboard.components.CovidChartStateProps
import dashboard.components.ReactChartData
import dashboard.selectors.dailySelectedSequences
import dashboard.selectors.offsetByKey
import dashboard.reducers.State
import libs.reselect.createSelector
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

private val getData: (State) -> ReactChartData =
    createSelector(
        State::dates,
        State::selectedDataTypes,
        { dailySelectedSequences(it) },
        { offsetByKey(it) },
        ::buildChartData
    )

val covidDailyChart: RClass<RProps> =
    rConnect<State, RAction, WrapperAction, RProps, CovidChartStateProps, RProps, CovidChartProps>(
        { state, _ ->
            type = "bar"
            data = getData(state)
        },
        { _, _ -> }
    )(CovidChart::class.js.unsafeCast<RClass<CovidChartProps>>())
