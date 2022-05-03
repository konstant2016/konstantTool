package com.konstant.develop.tree

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.konstant.develop.R

class TreeViewAdapter : TreeViewAdapterInterface {

    private val nodeList = mutableListOf<TreeNode>()
    private val arrowList = mutableListOf<TreeArrow>()

    fun setData(response: Response) {
        nodeList.clear()
        arrowList.clear()
        val nodeList = response.nodes.map { TreeNode(it.x, it.y) }
        this.nodeList.addAll(nodeList)
        response.relation.forEach { relation ->
            val start = response.nodes.find { it.id == relation.startId }
            val end = response.nodes.find { it.id == relation.endId }
            if (start != null && end != null) {
                arrowList.add(TreeArrow(start.x, start.y, end.x, end.y))
            }
        }
    }

    override fun getNodeList() = nodeList

    override fun getArrowList() = arrowList

    override fun onCreateView(context: Context, parent: ViewGroup, index: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_item_tree_view, parent, false)
        view.setOnClickListener {
            Log.d("ZoomLayout", "onClick")
            Toast.makeText(context, "点击了$index", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun getArrowColor(): Int {
        return Color.BLACK
    }

    override fun getArrowBitmap(context: Context): Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.drawable.icon_jiantou)
    }

    override fun getHorizontalStep() = 180

    override fun getVerticalStep() = 50

}