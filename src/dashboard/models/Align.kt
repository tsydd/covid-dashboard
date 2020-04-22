package dashboard.models

import dashboard.l10n.L10n
import dashboard.l10n.Translated

enum class Align : Translated {
    NONE,
    FIRST_CASE,
    FIRST_10_CASES,
    FIRST_50_CASES,
    FIRST_100_CASES,
    FIRST_500_CASES;

    override fun translate(language: L10n): String =
        when (this) {
            NONE -> language.alignBeginning
            FIRST_CASE -> language.alignFirstCase
            FIRST_10_CASES -> language.alignFirst10Cases
            FIRST_50_CASES -> language.alignFirst50Cases
            FIRST_100_CASES -> language.alignFirst100Cases
            FIRST_500_CASES -> language.alignFirst500Cases
        }

}
