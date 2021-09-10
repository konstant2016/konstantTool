package com.konstant.tool.ui.activity.cardSwipe

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.R
import com.konstant.tool.ui.activity.cardSwipe.demo02.*
import kotlinx.android.synthetic.main.activity_card_swipe.*
import kotlin.math.abs

/**
 * 时间：8/5/21 5:35 PM
 * 作者：吕卡
 * 备注：卡片滑动的页面，目前demo2的代码可用，滑动冲突问题已解决
 */

class CardSwipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_swipe)
        initViews()
    }

    private fun initViews() {
        val list = mutableListOf<String>()
        for (i in 0..18) {
            list.add("$i")
        }
        val settings = CardSetting()
        settings.setSwipeListener(object : OnSwipeCardListener<String> {

            var isTouching = false

            override fun onSwiping(viewHolder: RecyclerView.ViewHolder, dx: Float, dy: Float, direction: Int) {
                Log.d("CardSwipeActivity", "onSwiping :dx:$dx  ---- :$dy --- direction:$direction ")
                when {
                    abs(dx) < viewHolder.itemView.width / 3 -> {
                        view_bg.setBackgroundColor(Color.WHITE)
                    }
                    direction == ReItemTouchHelper.LEFT -> {
                        view_bg.setBackgroundColor(Color.RED)
                    }
                    direction == ReItemTouchHelper.RIGHT -> {
                        view_bg.setBackgroundColor(Color.GREEN)
                    }
                }

                if (!isTouching && dx > 100 && direction == 8 && viewHolder is SwipeCardAdapter.SwipeCardViewHolder) {
                    viewHolder.reverseCardToBg()
                }
            }

            override fun onSwipedOut(viewHolder: RecyclerView.ViewHolder, t: String, direction: Int) {
                Log.d("CardSwipeActivity", "onSwipedOut :direction:$direction")
                view_bg.setBackgroundColor(Color.WHITE)
            }

            override fun onSwipedClear() {

            }

            override fun onTouchUp(dx: Float, dy: Float, direction: Int) {
                Log.d("CardSwipeActivity", "onTouchUp :dx:$dx  ---- :$dy --- direction:$direction ")
                isTouching = false
            }

            override fun onStartSwipe() {
                isTouching = true
            }
        })
        val callback = CardTouchHelperCallback(recycler_view, list, settings)
        val itemTouchHelper = ReItemTouchHelper(callback)
        recycler_view.layoutManager = CardLayoutManager(itemTouchHelper, settings)
        val adapter = SwipeCardAdapter(list)
        recycler_view.adapter = adapter
    }
}