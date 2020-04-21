package dashboard

import dashboard.models.CovidData
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import libs.papaparse.Config
import libs.papaparse.Papa
import libs.papaparse.Result
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

data class RegionData(
    val region: String,
    val country: String,
    val values: List<Int>
) {
    val regionKey = if (region.isEmpty()) country else "$country $region"

    fun getKey(groupByCountry: Boolean) = when (groupByCountry) {
        true -> country
        else -> regionKey
    }
}

class FileData(
    val regionData: List<RegionData>,
    val dates: Array<String>
)

class CovidCsvData(
    val dates: List<String>,
    val data: CovidData<List<RegionData>>
)

const val deathsUrl: String =
    "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv"

const val confirmedUrl: String =
    "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv"

const val recoveredUrl: String =
    "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv"

suspend fun loadData(): CovidCsvData = coroutineScope {
    val loadDeathsJob = async { loadFile(deathsUrl) }
    val loadConfirmedJob = async { loadFile(confirmedUrl) }
    val loadRecoveredJob = async { loadFile(recoveredUrl) }
    CovidCsvData(
        dates = loadConfirmedJob.await().dates.toList(),
        data = CovidData(
            confirmed = loadConfirmedJob.await().regionData,
            recovered = loadRecoveredJob.await().regionData,
            deaths = loadDeathsJob.await().regionData,
            active = emptyList()
        )
    )
}

suspend fun loadFile(url: String): FileData {
    val csv = suspendCoroutine<Result> { continuation ->
        Papa.parse(
            url, Config(
                download = true,
                skipEmptyLines = true,
                complete = { res, _ -> continuation.resume(res) },
                error = { err, _ -> continuation.resumeWithException(RuntimeException(err.toString())) }
            ))
    }
    return FileData(
        dates = csv.data[0].drop(4).toTypedArray(),
        regionData = csv.data.asSequence()
            .drop(1)
            .map { row ->
                RegionData(
                    region = row[0],
                    country = row[1],
                    values = row.asSequence()
                        .drop(4)
                        .map { it.toInt() }
                        .toList()
                )
            }
            .toList()
    )
}
