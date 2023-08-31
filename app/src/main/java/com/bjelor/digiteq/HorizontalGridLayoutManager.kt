package com.bjelor.digiteq

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class HorizontalGridLayoutManager(
    private val rows: Int,
    private val columns: Int,
): LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = LayoutParams()

    class LayoutParams: RecyclerView.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.MATCH_PARENT,
    )

}