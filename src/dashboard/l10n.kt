package dashboard

import kotlin.browser.window

interface Translation {
    fun translate(language: L10n): String
}

interface L10n {
    val confirmed: String
    val recovered: String
    val deaths: String
    val active: String
    val align: String
    val alignBeginning: String
    val alignFirstCase: String
    val alignFirst10Cases: String
    val alignFirst50Cases: String
    val alignFirst100Cases: String
    val alignFirst500Cases: String
    val searchCountry: String
    val groupByCountry: String
    val country: String
    val countryRegion: String
    val tableHint: String
    val chartTitle: String
    val dailyChartTitle: String
}

private object English : L10n {
    override val confirmed: String
        get() = "Confirmed"
    override val recovered: String
        get() = "Recovered"
    override val deaths: String
        get() = "Deaths"
    override val active: String
        get() = "Active"
    override val align: String
        get() = "Align"
    override val alignBeginning: String
        get() = "From the beginning"
    override val alignFirstCase: String
        get() = "From first case"
    override val alignFirst10Cases: String
        get() = "From first 10 cases"
    override val alignFirst50Cases: String
        get() = "From first 50 cases"
    override val alignFirst100Cases: String
        get() = "From first 100 cases"
    override val alignFirst500Cases: String
        get() = "From first 500 cases"
    override val searchCountry: String
        get() = "SearchCountry"
    override val groupByCountry: String
        get() = "Group By Country"
    override val country: String
        get() = "Country"
    override val countryRegion: String
        get() = "Country/Region"
    override val tableHint: String
        get() = "*Table is scrollable. Click row to add country to chart"
    override val chartTitle: String
        get() = "Accumulative"
    override val dailyChartTitle: String
        get() = "Daily changes"
}

private object Russian : L10n {
    override val confirmed: String
        get() = "Подтверждено"
    override val recovered: String
        get() = "Вылечилось"
    override val deaths: String
        get() = "Умерло"
    override val active: String
        get() = "Болеет"
    override val align: String
        get() = "Отсчет дней"
    override val alignBeginning: String
        get() = "с начала"
    override val alignFirstCase: String
        get() = "с 1-го случая"
    override val alignFirst10Cases: String
        get() = "с 10-го случая"
    override val alignFirst50Cases: String
        get() = "с 50-го случая"
    override val alignFirst100Cases: String
        get() = "с 100-го случая"
    override val alignFirst500Cases: String
        get() = "с 500-го случая"
    override val searchCountry: String
        get() = "Искать страну"
    override val groupByCountry: String
        get() = "Группировать по стране"
    override val country: String
        get() = "Страна"
    override val countryRegion: String
        get() = "Страна/Регион"
    override val tableHint: String
        get() = "*Список прокручивается. Нажми на строку чтобы добавить страну на график"
    override val chartTitle: String
        get() = "График с накоплением"
    override val dailyChartTitle: String
        get() = "Изменения за сутки"
}

fun getL10n(): L10n = when (window.navigator.language) {
    "ru-RU" -> Russian
    else -> English
}
