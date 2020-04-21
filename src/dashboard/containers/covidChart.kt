package dashboard.containers

import dashboard.components.*
import dashboard.models.CovidData
import dashboard.models.CovidDataType
import dashboard.reducers.State
import dashboard.selectors.offsetByKey
import dashboard.selectors.selectedSequences
import libs.reselect.createSelector
import react.RClass
import react.RProps
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
    offsetByKey: Map<String, Int>
): ReactChartData {
    val allOffsets = sequences.confirmed.keys.asSequence()
        .map { offsetByKey[it] ?: 0 }
        .toSet()

    val labels = when {
        allOffsets.isEmpty() -> dates
        allOffsets.size == 1 -> dates.drop(allOffsets.first())
        else -> (0..dates.size - allOffsets.min()!!)
            .map { it.toString() }
    }

    val datasets = dataTypes.selected()
        .flatMap { dataType ->
            sequences[dataType].asSequence()
                .map { (key, values) ->
                    val offset = offsetByKey[key] ?: 0
                    ReactDataSet(
                        label = "${dataType.label} ($key)",
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
        ::buildChartData
    )

val covidChart: RClass<RProps> =
    rConnect<State, RAction, WrapperAction, RProps, CovidChartStateProps, RProps, CovidChartProps>(
        { state, _ ->
            type = "line"
            data = getData(state)
        },
        { _, _ -> }
    )(CovidChart::class.js.unsafeCast<RClass<CovidChartProps>>())
