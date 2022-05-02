package dashboard.models

data class GroupedData<T>(
    val byCountry: T,
    val byRegion: T
) {

    constructor(defaultValue: T) : this(
        byCountry = defaultValue,
        byRegion = defaultValue
    )

    fun <R> map(transform: (T) -> R): GroupedData<R> =
        GroupedData(
            byCountry = transform(byCountry),
            byRegion = transform(byRegion)
        )

    operator fun get(groupByCountry: Boolean): T = if (groupByCountry) byCountry else byRegion
}
