package com.example.musicbrainz.presentation.widget

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class RecyclerDivider(
    private val divider: Drawable
) : ItemDecoration() {

    override fun onDrawOver(
        canvas: Canvas,
        recycler: RecyclerView,
        state: RecyclerView.State
    ) {
        val childCount = recycler.childCount
        if (childCount < 2) return
        val dividerLeft = recycler.paddingLeft
        val dividerRight = recycler.width - recycler.paddingRight
        for (i in 0..childCount - 2) {
            drawDivider(
                recycler.getChildAt(i),
                dividerLeft,
                dividerRight,
                canvas
            )
        }
    }

    private fun drawDivider(
        child: View,
        dividerLeft: Int,
        dividerRight: Int,
        canvas: Canvas
    ) {
        val params = child.layoutParams as RecyclerView.LayoutParams
        val dividerTop = child.bottom + params.bottomMargin
        val dividerBottom = dividerTop + divider.intrinsicHeight
        divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
        divider.draw(canvas)
    }
}