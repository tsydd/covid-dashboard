package dashboard.models

import dashboard.l10n.L10n
import dashboard.l10n.Translated

enum class CovidDataType : Translated {
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
