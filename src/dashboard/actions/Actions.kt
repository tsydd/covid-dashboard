package dashboard.actions

import dashboard.CovidCsvData
import dashboard.components.SortColumn
import dashboard.models.Align
import dashboard.models.CovidDataType
import redux.RAction

class UpdateKeyFilter(val filter: String) : RAction

object ToggleGroupByCountry : RAction

class UpdateCovidData(val covidData: CovidCsvData) : RAction

class ToggleSort(val column: SortColumn) : RAction

class ToggleSelectedKey(val key: String) : RAction

class ToggleSelectedDataType(val dataType: CovidDataType) : RAction

class UpdateAlign(val align: Align) : RAction

class ResizeWindow(val width: Int, val height: Int) : RAction

class SwitchLanguage(val language: String) : RAction
