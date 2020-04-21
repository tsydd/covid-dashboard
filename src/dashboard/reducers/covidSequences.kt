package dashboard.reducers

import dashboard.models.CovidData
import dashboard.RegionData
import dashboard.actions.UpdateCovidData
import redux.RAction

fun covidSequences(
    state: CovidData<List<RegionData>> = CovidData(emptyList()),
    action: RAction
): CovidData<List<RegionData>> = when (action) {
    is UpdateCovidData -> {
        val groupedRecovered = action.covidData.data.recovered.associateBy { it.country to it.region }
        val groupedDeaths = action.covidData.data.deaths.associateBy { it.country to it.region }
        val active = action.covidData.data.confirmed.map { regionData ->
            val key = regionData.country to regionData.region
            val recovered = groupedRecovered[key]?.values
            val deaths = groupedDeaths[key]?.values

            val values = regionData.values.mapIndexed { index, value ->
                value - (recovered?.get(index) ?: 0) - (deaths?.get(index) ?: 0)
            }

            regionData.copy(values = values)
        }
        action.covidData.data.copy(active = active)
    }
    else -> state
}
