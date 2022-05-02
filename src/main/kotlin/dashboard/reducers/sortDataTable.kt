package dashboard.reducers

import dashboard.actions.ToggleSort
import dashboard.components.Sort
import dashboard.components.SortColumn
import dashboard.components.SortOrder
import redux.RAction

fun sortDataTable(
    state: Sort = Sort(
        column = SortColumn.NAME,
        order = SortOrder.ASC
    ), action: RAction
): Sort = when (action) {
    is ToggleSort -> {
        if (action.column == state.column) {
            state.copy(order = !state.order)
        } else {
            Sort(
                column = action.column,
                order = if (action.column == SortColumn.NAME) {
                    SortOrder.ASC
                } else {
                    SortOrder.DESC
                }
            )
        }
    }
    else -> state
}
