package dashboard.selectors

import dashboard.models.Align
import dashboard.models.CovidData
import dashboard.models.GroupedData
import dashboard.reducers.State
import libs.reselect.createSelector

fun <T> List<T>.indexOfFirstOrLength(predicate: (T) -> Boolean): Int = when (val index = indexOfFirst(predicate)) {
    -1 -> size
    else -> index
}

fun groupedFirstNCasesOffsets(
    sequences: GroupedData<CovidData<Map<String, List<Int>>>>,
    count: Int
): GroupedData<Map<String, Int>> = sequences.map { grouped ->
    grouped.confirmed.mapValues { (_, list) ->
        list.indexOfFirstOrLength { it > count }
    }
}

fun createFirstNCasesOffsetSelector(count: Int): (State) -> GroupedData<Map<String, Int>> = createSelector(
    { it: State -> groupedSequences(it) },
    { groupedFirstNCasesOffsets(it, count) }
)

val offsetByKey: (State) -> Map<String, Int> =
    createSelector(
        State::align,
        State::groupByCountry,
        createFirstNCasesOffsetSelector(0),
        createFirstNCasesOffsetSelector(10),
        createFirstNCasesOffsetSelector(50),
        createFirstNCasesOffsetSelector(100),
        createFirstNCasesOffsetSelector(500)
    ) { align, groupByCountry, first, first10, first50, first100, first500 ->
        val offsets: GroupedData<Map<String, Int>> = when (align) {
            Align.NONE -> GroupedData(emptyMap())
            Align.FIRST_CASE -> first
            Align.FIRST_10_CASES -> first10
            Align.FIRST_50_CASES -> first50
            Align.FIRST_100_CASES -> first100
            Align.FIRST_500_CASES -> first500
        }
        offsets[groupByCountry]
    }
