package dashboard.l10n

import kotlin.browser.window

fun getL10n(): L10n = when (window.navigator.language.toLowerCase()) {
    "ru-ru", "ru" -> Russian
    else -> English
}
