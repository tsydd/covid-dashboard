package dashboard.selectors

import dashboard.RegionData
import dashboard.models.CovidData
import dashboard.models.GroupedData
import dashboard.reducers.State
import libs.reselect.createSelector

fun List<RegionData>.sums(): List<Int> =
    if (isEmpty()) {
        emptyList()
    } else {
        first().values.indices.map { index ->
            sumBy { it.values[index] }
        }
    }

fun groupByCountry(sequences: CovidData<List<RegionData>>): CovidData<Map<String, List<Int>>> {
    return sequences.map { list ->
        list.groupBy(RegionData::country)
            .mapValues { (_, groupedRegionData) -> groupedRegionData.sums() }
    }
}

fun groupByRegion(sequences: CovidData<List<RegionData>>): CovidData<Map<String, List<Int>>> {
    return sequences.map { list ->
        list.groupBy(RegionData::regionKey)
            .mapValues { (_, groupedRegionData) -> groupedRegionData.sums() }
    }
}

val groupedSequences: (State) -> GroupedData<CovidData<Map<String, List<Int>>>> =
    createSelector(
        State::covidSequences
    ) { sequences ->
        GroupedData(
            byCountry = groupByCountry(sequences),
            byRegion = groupByRegion(sequences)
        )
    }

val globalSequence: (State) -> CovidData<Map<String, List<Int>>> =
    createSelector(
        State::covidSequences
    ) { sequences ->
        sequences.map { list ->
            mapOf("Global" to list.sums())
        }
    }

fun buildSelectedSequences(
    selectedKeys: Set<String>,
    groupByCountry: Boolean,
    groupedSequences: GroupedData<CovidData<Map<String, List<Int>>>>,
    globalSequence: CovidData<Map<String, List<Int>>>
): CovidData<Map<String, List<Int>>> {
    return if (selectedKeys.isEmpty()) {
        globalSequence
    } else {
        groupedSequences[groupByCountry].map {
            it.filterKeys { key -> key in selectedKeys }
        }
    }
}

val selectedSequences: (State) -> CovidData<Map<String, List<Int>>> =
    createSelector(
        State::selectedKeys,
        State::groupByCountry,
        groupedSequences,
        globalSequence,
        ::buildSelectedSequences
    )

