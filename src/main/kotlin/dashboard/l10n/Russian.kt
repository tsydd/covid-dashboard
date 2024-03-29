package dashboard.l10n

object Russian : L10n {
    override val title: String
        get() = "COVID-19 - графики и статистика по коронавирусу. Сравнение нескольких стран на одном графике"
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
    override val alignFirst1000Cases: String
        get() = "с 1000-го случая"
    override val alignLast3Days: String
        get() = "Последние 3 дня"
    override val alignLastWeek: String
        get() = "Последняя неделя"
    override val alignLast2Weeks: String
        get() = "Последние 2 недели"
    override val alignLastMonth: String
        get() = "Последний месяц"
    override val alignLast3Months: String
        get() = "Последние 3 месяца"
    override val alignLast6Months: String
        get() = "Последние 6 месяцев"
    override val alignLastYear: String
        get() = "Последний год"
    override val alignLast1_5Years: String
        get() = "Последние 1.5 года"
    override val alignLast2Years: String
        get() = "Последние 2 года"
    override val searchCountry: String
        get() = "Искать страну"
    override val groupByCountry: String
        get() = "Группировать по стране"
    override val country: String
        get() = "Страна"
    override val countryRegion: String
        get() = "Страна/Регион"
    override val tableHint: String
        get() = "*Список прокручивается. Нажми на строку, чтобы добавить страну на график"
    override val chartTitle: String
        get() = "График с накоплением"
    override val dailyChartTitle: String
        get() = "Изменения за сутки"
    override val newConfirmed: String
        get() = "Новые подтвержденные"
    override val newRecovered: String
        get() = "Новые вылечившиеся"
    override val newDeaths: String
        get() = "Новые умершие"
    override val newActive: String
        get() = "Новые больные"
    override val updated: String
        get() = "Обновлено"
}
