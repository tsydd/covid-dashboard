package dashboard.utils

fun Int.toStringWithSign(): String =
    if (this >= 0) {
        "+$this"
    } else {
        this.toString()
    }
