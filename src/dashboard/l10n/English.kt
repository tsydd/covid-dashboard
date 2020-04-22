package dashboard.l10n

object English : L10n {
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
    override val alignFirst1000Cases: String
        get() = "From first 1000 cases"
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
