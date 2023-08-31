package com.bjelor.digiteq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeGrid()
    }

    private fun initializeGrid() {
        findViewById<RecyclerView>(R.id.grid)?.let { grid ->
            grid.layoutManager = LinearLayoutManager(this)
            grid.adapter = HorizontalGridAdapter(50)
        }
    }
}