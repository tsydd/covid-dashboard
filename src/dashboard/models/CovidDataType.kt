package dashboard.models

enum class CovidDataType(val label: String) {
    CONFIRMED("Confirmed"),
    RECOVERED("Recovered"),
    DEATHS("Deaths"),
    ACTIVE("Active")
}
