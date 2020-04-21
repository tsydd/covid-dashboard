package dashboard.selectors

import dashboard.RegionData
import dashboard.models.CovidData
import dashboard.models.GroupedData
import dashboard.reducers.State
import libs.reselect.createSelector

val dailySequences: (State) -> CovidData<List<RegionData>> =
    createSelector(
        State::covidSequences
    ) { sequences ->
        sequences.map { list ->
            list.map { regionData ->
                regionData.copy(
                    values = (sequenceOf(0) + regionData.values.asSequence())
                        .zipWithNext { a, b -> b - a }
                        .toList()
                )
            }
        }
    }

val dailyGroupedSequences: (State) -> GroupedData<CovidData<Map<String, List<Int>>>> =
    createSelector(
        dailySequences
    ) { sequences ->
        GroupedData(
            byCountry = groupByCountry(sequences),
            byRegion = groupByRegion(sequences)
        )
    }

val globalDailySequences: (State) -> CovidData<Map<String, List<Int>>> =
    createSelector(
        dailySequences
    ) { sequences ->
        sequences.map { list ->
            mapOf("Global" to list.sums())
        }
    }

val dailySelectedSequences: (State) -> CovidData<Map<String, List<Int>>> =
    createSelector(
        State::selectedKeys,
        State::groupByCountry,
        dailyGroupedSequences,
        globalDailySequences,
        ::buildSelectedSequences
    )
