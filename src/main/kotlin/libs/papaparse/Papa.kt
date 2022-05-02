package libs.papaparse

class Result(
    val data: Array<Array<String>>
)

data class Config(
    val download: Boolean = false,
    val skipEmptyLines: Boolean = false,
    val complete: (Result, String) -> Unit,
    val error: (Any, String) -> Unit
);

@JsModule("papaparse")
@JsNonModule
external class Papa {
    companion object {
        fun parse(url: String, config: Config)
    }
}
