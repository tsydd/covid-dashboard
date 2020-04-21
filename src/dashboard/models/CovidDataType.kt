package dashboard.models

import dashboard.L10n
import dashboard.Translation

enum class CovidDataType : Translation {
    CONFIRMED,
    RECOVERED,
    DEATHS,
    ACTIVE;

    override fun translate(language: L10n): String = when (this) {
        CONFIRMED -> language.confirmed
        RECOVERED -> language.recovered
        DEATHS -> language.deaths
        ACTIVE -> language.active
    }
}
