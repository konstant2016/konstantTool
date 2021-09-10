package com.konstant.tool.ui.activity.cardSwipe

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.R
import kotlinx.android.synthetic.main.item_card_swipe.view.*


class SwipeCardAdapter(private val list: MutableList<String>) : RecyclerView.Adapter<SwipeCardAdapter.SwipeCardViewHolder>() {

    class SwipeCardViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun reverseCardToBg() {
            val outAnimation = AnimatorInflater.loadAnimator(view.context, R.anim.anim_out) as AnimatorSet
            val inAnimation = AnimatorInflater.loadAnimator(view.context, R.anim.anim_in) as AnimatorSet
            if (view.card_fg.visibility == View.GONE) return
            outAnimation.setTarget(view.card_fg)
            inAnimation.setTarget(view.card_bg)
            outAnimation.start()
            inAnimation.start()
            view.postDelayed({
                view.card_fg.visibility = View.GONE
                view.card_bg.visibility = View.VISIBLE
            },800)
        }

        fun reverseCard(){
            val animOut = AnimationUtils.loadAnimation(view.context, R.anim.anim_out)
            val animIn = AnimationUtils.loadAnimation(view.context, R.anim.anim_in)

            animOut.setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    view.card_fg.visibility = View.GONE
                    view.card_bg.startAnimation(animIn)
                }

                override fun onAnimationStart(animation: Animation?) {

                }

            })

            animIn.setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {

                }

                override fun onAnimationStart(animation: Animation?) {
                    view.card_bg.visibility = View.VISIBLE
                }

            })
            view.card_fg.startAnimation(animOut)
//            view.card_bg.startAnimation(animIn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_swipe, parent, false)
        view.setOnClickListener {
            Log.d("onCreateViewHolder", "OnClickListener")
        }
        return SwipeCardViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SwipeCardViewHolder, position: Int) {
        holder.itemView.tv_content.text = "${list[position]}/${itemCount}"
        holder.itemView.tv_content_bg.text = "${list[position]}/${itemCount}"
        setCameraDistance(holder)
    }

    // 改变视角距离, 贴近屏幕
    private fun setCameraDistance(holder: SwipeCardViewHolder) {
        val distance = 16000
        val scale: Float = holder.itemView.context.resources.displayMetrics.density * distance
        holder.itemView.card_fg.cameraDistance = scale
        holder.itemView.card_bg.cameraDistance = scale
    }
}