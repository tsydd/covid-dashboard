package dashboard.l10n

import kotlin.browser.window

fun getL10n(): L10n = when (window.navigator.language) {
    "ru-RU" -> Russian
    else -> English
}
