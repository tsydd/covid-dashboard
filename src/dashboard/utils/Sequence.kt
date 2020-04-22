package dashboard.utils

fun <T> Sequence<T>.toArray(): Array<T> {
    val result = arrayOf<T>()
    val dynamicResult = result.asDynamic()
    forEach {
        dynamicResult.push(it)
        Unit
    }
    return result
}
