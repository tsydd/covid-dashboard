package dashboard.containers

import dashboard.components.CovidChart
import dashboard.components.CovidChartProps
import dashboard.components.CovidChartStateProps
import dashboard.components.ReactChartData
import dashboard.components.ReactDataSet
import dashboard.l10n.L10n
import dashboard.models.CovidData
import dashboard.models.CovidDataType
import dashboard.reducers.State
import dashboard.selectors.aspectRatio
import dashboard.selectors.offsetByKey
import dashboard.selectors.selectedSequences
import libs.reselect.createSelector
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

fun CovidData<Boolean>.selected(): Sequence<CovidDataType> =
    CovidDataType.values().asSequence()
        .filter { get(it) }

fun buildChartData(
    dates: List<String>,
    dataTypes: CovidData<Boolean>,
    sequences: CovidData<Map<String, List<Int>>>,
    offsetByKey: Map<String, Int>,
    translation: L10n
): ReactChartData {
    val allOffsets = sequences.confirmed.keys.asSequence()
        .map { offsetByKey[it] ?: 0 }
        .toSet()

    val labels = when {
        allOffsets.isEmpty() -> dates
        allOffsets.size == 1 -> dates.drop(allOffsets.first())
        else -> (0..dates.size - allOffsets.minOrNull()!!)
            .map { it.toString() }
    }

    val datasets = dataTypes.selected()
        .flatMap { dataType ->
            sequences[dataType].asSequence()
                .map { (key, values) ->
                    val offset = offsetByKey[key] ?: 0
                    ReactDataSet(
                        label = "${dataType.translate(translation)} ($key)",
                        data = values.drop(offset)
                    )
                }
        }
        .toList()
    return ReactChartData(
        labels = labels,
        datasets = datasets
    )
}

private val getData: (State) -> ReactChartData =
    createSelector(
        State::dates,
        State::selectedDataTypes,
        { selectedSequences(it) },
        { offsetByKey(it) },
        State::translation,
        ::buildChartData
    )

val covidChart: ComponentClass<Props> =
    rConnect<State, RAction, WrapperAction, Props, CovidChartStateProps, Props, CovidChartProps>(
        { state, _ ->
            type = "line"
            data = getData(state)
            title = state.translation.chartTitle
            aspectRatio = aspectRatio(state)
        },
        { _, _ -> }
    )(CovidChart::class.js.unsafeCast<ComponentClass<CovidChartProps>>())
