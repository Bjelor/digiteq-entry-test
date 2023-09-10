package com.bjelor.digiteq

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = HorizontalGridLayoutManager(3, 5)
        val animation: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(
            this,
            R.anim.layout_animation_scale_in
        )

        val grid = findViewById<RecyclerView>(R.id.grid)?.also { grid ->
            grid.layoutManager = layoutManager
            grid.layoutAnimation = animation
            grid.adapter = HorizontalGridAdapter(20)
        }

        findViewById<Button>(R.id.button)?.let { button ->
            button.setOnClickListener {
                grid?.smoothScrollToPosition(49)
            }
        }
    }

}