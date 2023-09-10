package com.bjelor.digiteq

import android.graphics.PointF
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlin.math.max
import kotlin.math.min

/**
 * The class is based on the code from the guide below
 * https://wiresareobsolete.com/2014/09/building-a-recyclerview-layoutmanager-part-1/
 *
 * Also, got some inspiration from this guide
 * https://medium.com/android-development-by-danylo/10-steps-to-create-a-custom-layoutmanager-2f30ab2f979d
 *
 */
class HorizontalGridLayoutManager(
    private val rows: Int,
    private val columns: Int,
) : LayoutManager() {

    private var itemWidth: Int = 0
    private var itemHeight: Int = 0

    private val horizontalSpace: Int
        get() = width - paddingRight - paddingLeft

    private var scrollDelta: Int = 0

    private val sectionSize: Int = rows * columns

    private val rightmostItemPosition: Int
        get() {
            val lastSectionWidth = min(columns, childCount % sectionSize)
            return (itemCount / sectionSize) * sectionSize + lastSectionWidth - 1
        }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler)
            return
        }
        if (childCount == 0 && state.isPreLayout) {
            return
        }
        if (childCount == 0) {
            val measuredItem = recycler.getViewForPosition(0)
            addView(measuredItem)
            measureChildWithMargins(measuredItem, 0, 0)

            itemWidth = getDecoratedMeasuredWidth(measuredItem)
            itemHeight = getDecoratedMeasuredHeight(measuredItem)
            detachAndScrapView(measuredItem, recycler)
        }

        detachAndScrapAttachedViews(recycler)

        val firstStartOffset = paddingLeft
        val firstTopOffset = paddingTop

        var leftOffset = firstStartOffset
        var topOffset = firstTopOffset
        for (i in 1 .. itemCount) {
            val view = recycler.getViewForPosition(i - 1)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            layoutDecorated(
                view,
                leftOffset,
                topOffset,
                leftOffset + itemWidth,
                topOffset + itemHeight
            )

            val section = i / sectionSize
            val startNextRow = i % columns == 0
            val startNextSection = i % sectionSize == 0

            if (startNextSection) {
                leftOffset = section * columns * itemWidth
                topOffset = firstTopOffset
            } else if (startNextRow) {
                leftOffset = section * columns * itemWidth
                topOffset += itemHeight
            } else {
                leftOffset += itemWidth
            }
        }

    }

    override fun canScrollHorizontally(): Boolean = true

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        if (childCount == 0) {
            return 0
        }

        val leftmostView = getChildAt(0) ?: return 0
        val rightmostView = getChildAt(rightmostItemPosition) ?: return 0

        val viewSpan = getDecoratedRight(rightmostView) - getDecoratedLeft(leftmostView)
        if (viewSpan < horizontalSpace) {
            return 0
        }

        val leftBoundReached = scrollDelta <= 0
        val rightBoundReached = scrollDelta + horizontalSpace >= viewSpan
        val delta = if (dx > 0) {
            // Contents are scrolling left
            if (rightBoundReached) {
                val rightOffset = horizontalSpace - getDecoratedRight(rightmostView) + paddingRight
                max(-dx, rightOffset)
            } else {
                -dx
            }
        } else {
            // Contents are scrolling right
            if (leftBoundReached) {
                val leftOffset = -getDecoratedLeft(leftmostView) + paddingLeft
                min(-dx, leftOffset)
            } else {
                -dx
            }
        }
        offsetChildrenHorizontal(delta)
        scrollDelta -= delta
        return -delta
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        if (position >= itemCount) {
            return
        }

        val scroller: LinearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {

            override fun computeScrollVectorForPosition(targetPosition: Int): PointF {
                val targetColumn = (position / rows) + ((position % sectionSize) % columns) - 1
                val horizontalScrollOffset = (targetColumn * itemWidth).toFloat()
                return PointF(
                    horizontalScrollOffset,
                    0f,
                )
            }
        }
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = LayoutParams()

    class LayoutParams : RecyclerView.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.MATCH_PARENT,
    )

}