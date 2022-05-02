package dashboard.components

import dashboard.utils.generatePalette
import dashboard.utils.toArray
import libs.chartjs.*
import react.*
import react.dom.html.ReactHTML.canvas

data class ReactDataSet(
    val label: String,
    val data: List<Int>
)

data class ReactChartData(
    val labels: List<String>,
    val datasets: List<ReactDataSet>
)

external interface CovidChartStateProps : Props {
    var title: String
    var type: String
    var data: ReactChartData
    var aspectRatio: Int
}

external interface CovidChartProps : CovidChartStateProps

class CovidChart(props: CovidChartProps) : RComponent<CovidChartProps, State>(props) {
    private var chartRef: RefObject<dynamic> = createRef()
    private var chart: Chart? = null

    override fun componentDidMount() {
        rebuildChart()
    }

    override fun componentDidUpdate(prevProps: CovidChartProps, prevState: State, snapshot: Any) {
        rebuildChart()
    }

    private fun rebuildChart() {
        val ctx = chartRef.current.getContext("2d")
        chart?.destroy()

        val colors = generatePalette()

        chart = Chart(
            ctx = ctx,
            config = Config(
                type = props.type,
                data = Data(
                    labels = props.data.labels.toTypedArray(),
                    datasets = props.data.datasets.asSequence().zip(colors)
                        .map { (dataSet, color) ->
                            DataSet(
                                label = dataSet.label,
                                data = dataSet.data.toTypedArray(),
                                borderColor = color,
                                backgroundColor = color
                            )
                        }
                        .toArray()
                ),
                options = Options(
                    animation = AnimationOptions(
                        duration = 0
                    ),
                    title = TitleOptions(
                        text = props.title
                    ),
                    aspectRatio = props.aspectRatio
                )
            )
        )
    }

    override fun RBuilder.render() {
        canvas {
            ref = chartRef
        }
    }
}
