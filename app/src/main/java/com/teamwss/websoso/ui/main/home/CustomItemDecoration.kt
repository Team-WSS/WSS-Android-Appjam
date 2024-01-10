package com.teamwss.websoso.ui.main.home

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    private val marginStartEnd = dpToPx(context, 20)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position == 0) {
            outRect.left = marginStartEnd
        }

        if (position == itemCount - 1) {
            outRect.right = marginStartEnd
        }
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}