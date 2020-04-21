package libs.chartjs

class DataSet(
    val label: String,
    val data: Array<out Number>,
    val fill: Boolean = false,
    val borderColor: String,
    val backgroundColor: String? = undefined
)

class Data(
    val labels: Array<String>,
    val datasets: Array<DataSet>
)

class AnimationOptions(
    val duration: Int
)

class Options(
    val animation: AnimationOptions,
    val aspectRatio: Int = 7
)

class Config(
    val type: String,
    val data: Data,
    val options: Options
)

@JsModule("chart.js")
external class Chart(
    ctx: dynamic,
    config: Config
) {
    fun destroy()
}
