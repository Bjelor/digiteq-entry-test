package com.bjelor.digiteq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HorizontalGridAdapter(
    private val itemCount: Int,
): RecyclerView.Adapter<HorizontalGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.adapter_item_horizontal_grid, parent, false)
            .let {
                ViewHolder(it)
            }

    override fun getItemCount(): Int = itemCount

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setText(position.toString())
    }

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun setText(text: String) {
            (view as? TextView)?.let { gridItem ->
                gridItem.text = text
            }
        }
    }

}