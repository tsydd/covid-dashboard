package dashboard.models

import dashboard.l10n.L10n
import dashboard.l10n.Translated

enum class Align : Translated {
    NONE,
    FIRST_CASE,
    FIRST_10_CASES,
    FIRST_50_CASES,
    FIRST_100_CASES,
    FIRST_500_CASES,
    FIRST_1000_CASES,
    LAST_3_DAYS,
    LAST_WEEK,
    LAST_2_WEEKS,
    LAST_MONTH,
    LAST_3_MONTHS,
    LAST_6_MONTHS,
    LAST_YEAR,
    LAST_1_5_YEARS,
    LAST_2_YEARS,
    ;

    override fun translate(language: L10n): String =
        when (this) {
            NONE -> language.alignBeginning
            FIRST_CASE -> language.alignFirstCase
            FIRST_10_CASES -> language.alignFirst10Cases
            FIRST_50_CASES -> language.alignFirst50Cases
            FIRST_100_CASES -> language.alignFirst100Cases
            FIRST_500_CASES -> language.alignFirst500Cases
            FIRST_1000_CASES -> language.alignFirst1000Cases
            LAST_3_DAYS -> language.alignLast3Days
            LAST_WEEK -> language.alignLastWeek
            LAST_2_WEEKS -> language.alignLast2Weeks
            LAST_MONTH -> language.alignLastMonth
            LAST_3_MONTHS -> language.alignLast3Months
            LAST_6_MONTHS -> language.alignLast6Months
            LAST_YEAR -> language.alignLastYear
            LAST_1_5_YEARS -> language.alignLast1_5Years
            LAST_2_YEARS -> language.alignLast2Years
        }
}
