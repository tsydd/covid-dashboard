package dashboard.selectors

import dashboard.models.Align
import dashboard.models.CovidData
import dashboard.models.GroupedData
import dashboard.reducers.State
import libs.reselect.createSelector
import kotlin.math.max

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

fun createFirstNCasesOffsetSelector(count: Int): (State) -> GroupedData<Map<String, Int>> =
    createSelector(
        { groupedSequences(it) }
    ) {
        groupedFirstNCasesOffsets(it, count)
    }

fun createLastNDaysOffsetSelector(count: Int): (State) -> GroupedData<Map<String, Int>> =
    createSelector(
        { groupedSequences(it) },
        State::dates
    ) { sequences, dates ->
        val offset = max(dates.size - count, 0)
        sequences.map { grouped ->
            grouped.confirmed.mapValues { offset }
        }
    }


val offsetByKey: (State) -> Map<String, Int> =
    createSelector(
        State::align,
        State::groupByCountry,
        createFirstNCasesOffsetSelector(0),
        createFirstNCasesOffsetSelector(10),
        createFirstNCasesOffsetSelector(50),
        createFirstNCasesOffsetSelector(100),
        createFirstNCasesOffsetSelector(500),
        createFirstNCasesOffsetSelector(1000),
        createLastNDaysOffsetSelector(3),
        createLastNDaysOffsetSelector(7),
        createLastNDaysOffsetSelector(14),
        createLastNDaysOffsetSelector(30),
        createLastNDaysOffsetSelector(90),
        createLastNDaysOffsetSelector(180),
        createLastNDaysOffsetSelector(365),
        createLastNDaysOffsetSelector(547),
        createLastNDaysOffsetSelector(730),
    ) { align, groupByCountry, first, first10, first50, first100, first500, first1000, last3Days, lastWeek, last2Weeks, lastMonth, last3Months, last6Months, lastYear, last1_5Years, last2Years ->
        val offsets: GroupedData<Map<String, Int>> = when (align) {
            Align.NONE -> GroupedData(emptyMap())
            Align.FIRST_CASE -> first
            Align.FIRST_10_CASES -> first10
            Align.FIRST_50_CASES -> first50
            Align.FIRST_100_CASES -> first100
            Align.FIRST_500_CASES -> first500
            Align.FIRST_1000_CASES -> first1000
            Align.LAST_3_DAYS -> last3Days
            Align.LAST_WEEK -> lastWeek
            Align.LAST_2_WEEKS -> last2Weeks
            Align.LAST_MONTH -> lastMonth
            Align.LAST_3_MONTHS -> last3Months
            Align.LAST_6_MONTHS -> last6Months
            Align.LAST_YEAR -> lastYear
            Align.LAST_1_5_YEARS -> last1_5Years
            Align.LAST_2_YEARS -> last2Years
        }
        offsets[groupByCountry]
    }
