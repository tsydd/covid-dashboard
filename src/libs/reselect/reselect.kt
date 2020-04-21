@file:JsModule("reselect")

package libs.reselect

external fun <S, R1, T> createSelector(
    selector: (S) -> R1,
    combiner: (R1) -> T
): (S) -> T

external fun <S, R1, R2, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    combiner: (R1, R2) -> T
): (S) -> T

external fun <S, R1, R2, R3, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    selector3: (S) -> R3,
    combiner: (R1, R2, R3) -> T
): (S) -> T

external fun <S, R1, R2, R3, R4, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    selector3: (S) -> R3,
    selector4: (S) -> R4,
    combiner: (R1, R2, R3, R4) -> T
): (S) -> T

external fun <S, R1, R2, R3, R4, R5, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    selector3: (S) -> R3,
    selector4: (S) -> R4,
    selector5: (S) -> R5,
    combiner: (R1, R2, R3, R4, R5) -> T
): (S) -> T

external fun <S, R1, R2, R3, R4, R5, R6, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    selector3: (S) -> R3,
    selector4: (S) -> R4,
    selector5: (S) -> R5,
    selector6: (S) -> R6,
    combiner: (R1, R2, R3, R4, R5, R6) -> T
): (S) -> T

external fun <S, R1, R2, R3, R4, R5, R6, R7, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    selector3: (S) -> R3,
    selector4: (S) -> R4,
    selector5: (S) -> R5,
    selector6: (S) -> R6,
    selector7: (S) -> R7,
    combiner: (R1, R2, R3, R4, R5, R6, R7) -> T
): (S) -> T
