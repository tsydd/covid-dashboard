package dashboard.l10n

import kotlinx.browser.window

fun getL10n(): L10n = when (window.navigator.language.lowercase()) {
    "ru-ru", "ru" -> Russian
    else -> English
}
