package dashboard.models

data class CovidData<T>(
    val confirmed: T,
    val recovered: T,
    val deaths: T,
    val active: T
) {
    constructor(initialValue: T) : this(
        confirmed = initialValue,
        recovered = initialValue,
        deaths = initialValue,
        active = initialValue
    )

    fun <R> map(transform: (T) -> R): CovidData<R> =
        CovidData(
            confirmed = transform(confirmed),
            recovered = transform(recovered),
            deaths = transform(deaths),
            active = transform(active)
        )

    operator fun get(type: CovidDataType): T = when (type) {
        CovidDataType.CONFIRMED -> confirmed
        CovidDataType.RECOVERED -> recovered
        CovidDataType.DEATHS -> deaths
        CovidDataType.ACTIVE -> active
    }

    fun with(type: CovidDataType, value: T): CovidData<T> = when (type) {
        CovidDataType.CONFIRMED -> copy(confirmed = value)
        CovidDataType.RECOVERED -> copy(recovered = value)
        CovidDataType.DEATHS -> copy(deaths = value)
        CovidDataType.ACTIVE -> copy(active = value)
    }
}
