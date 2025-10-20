package com.halimjr11.cameo.components

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Reusable ItemDecoration to add spacing between RecyclerView items.
 *
 * @param spacing the spacing size in pixels.
 * @param orientation vertical (RecyclerView.VERTICAL) or horizontal (RecyclerView.HORIZONTAL)
 * @param includeEdge whether to apply spacing to the edges (start & end)
 */
class SpacingItemDecoration(
    private val spacing: Int,
    private val orientation: Int = RecyclerView.VERTICAL,
    private val includeEdge: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (orientation == RecyclerView.VERTICAL) {
            if (includeEdge) {
                outRect.top = if (position == 0) spacing else spacing / 2
                outRect.bottom = if (position == itemCount - 1) spacing else spacing / 2
                outRect.left = spacing
                outRect.right = spacing
            } else {
                outRect.top = if (position == 0) 0 else spacing
            }
        } else {
            // Horizontal orientation
            if (includeEdge) {
                outRect.left = if (position == 0) spacing else spacing / 2
                outRect.right = if (position == itemCount - 1) spacing else spacing / 2
                outRect.top = spacing
                outRect.bottom = spacing
            } else {
                outRect.left = if (position == 0) 0 else spacing
            }
        }
    }
}