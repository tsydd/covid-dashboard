@file:JsModule("reselect")
@file:JsNonModule

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

external fun <S, R1, R2, R3, R4, R5, R6, R7, R8, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    selector3: (S) -> R3,
    selector4: (S) -> R4,
    selector5: (S) -> R5,
    selector6: (S) -> R6,
    selector7: (S) -> R7,
    selector8: (S) -> R8,
    combiner: (R1, R2, R3, R4, R5, R6, R7, R8) -> T
): (S) -> T

external fun <S, R1, R2, R3, R4, R5, R6, R7, R8, R9, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    selector3: (S) -> R3,
    selector4: (S) -> R4,
    selector5: (S) -> R5,
    selector6: (S) -> R6,
    selector7: (S) -> R7,
    selector8: (S) -> R8,
    selector9: (S) -> R9,
    combiner: (R1, R2, R3, R4, R5, R6, R7, R8, R9) -> T
): (S) -> T

external fun <S, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, T> createSelector(
    selector1: (S) -> R1,
    selector2: (S) -> R2,
    selector3: (S) -> R3,
    selector4: (S) -> R4,
    selector5: (S) -> R5,
    selector6: (S) -> R6,
    selector7: (S) -> R7,
    selector8: (S) -> R8,
    selector9: (S) -> R9,
    selector10: (S) -> R10,
    selector11: (S) -> R11,
    selector12: (S) -> R12,
    selector13: (S) -> R13,
    selector14: (S) -> R14,
    selector15: (S) -> R15,
    selector16: (S) -> R16,
    selector17: (S) -> R17,
    combiner: (R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17) -> T
): (S) -> T
