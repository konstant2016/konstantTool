package com.konstant.develop.dsl.selector

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.SimpleItemAnimator
import com.konstant.develop.R
import kotlinx.android.synthetic.main.layout_dialog_selector.*

/**
 * 时间：2022/3/18 5:13 下午
 * 作者：吕卡
 * 备注：教材选择的弹窗
 */

class SelectorDialog : DialogFragment() {

    private val mStageList = mutableListOf<Stage>()

    fun setStageList(list: List<Stage>) {
        mStageList.clear()
        mStageList.addAll(list)
    }

    override fun onStart() {
        super.onStart()
        val height = getScreenHeight(requireActivity()) / 2
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.layout_dialog_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        updateStageList(mStageList)
        updatePublishList(mStageList[0].publishers)
        updateSemesterList(mStageList[0].publishers[0].semesters)
    }

    private fun setViews() {
        recycler_title.adapter = AdapterSelectorTitle()
        recycler_title.itemAnimator?.apply {
            changeDuration = 0
            addDuration = 0
            removeDuration = 0
            moveDuration = 0
            if (this is SimpleItemAnimator) {
                supportsChangeAnimations = false
            }
        }
        recycler_publisher.adapter = AdapterSelectorPublish()
        recycler_publisher.itemAnimator?.apply {
            changeDuration = 0
            addDuration = 0
            removeDuration = 0
            moveDuration = 0
            if (this is SimpleItemAnimator) {
                supportsChangeAnimations = false
            }
        }
        recycler_semester.adapter = AdapterSelectorSemester()
        recycler_semester.itemAnimator?.apply {
            changeDuration = 0
            addDuration = 0
            removeDuration = 0
            moveDuration = 0
            if (this is SimpleItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun updateStageList(stageList: List<Stage>) {
        val selected = stageList.any { it.selected }
        if (!selected && stageList.isNotEmpty()) {
            stageList[0].selected = true
        }
        val adapter = recycler_title.adapter
        if (adapter is AdapterSelectorTitle) {
            adapter.setData(stageList)
            adapter.setOnItemClickListener {
                updatePublishList(stageList[it].publishers)
            }
        }
    }

    private fun updatePublishList(publishList: List<Publisher>) {
        val selected = publishList.any { it.selected }
        if (!selected && publishList.isNotEmpty()) {
            publishList[0].selected = true
        }
        val adapter = recycler_publisher.adapter
        if (adapter is AdapterSelectorPublish) {
            adapter.setData(publishList)
            adapter.setOnItemClickListener {
                updateSemesterList(publishList[it].semesters)
            }
        }
    }

    private fun updateSemesterList(semesterList: List<Semester>) {
        val selected = semesterList.any { it.selected }
        if (!selected && semesterList.isNotEmpty()) {
            semesterList[0].selected = true
        }
        val adapter = recycler_semester.adapter
        if (adapter is AdapterSelectorSemester) {
            adapter.setData(semesterList)
            adapter.setOnItemClickListener {
                val stage = mStageList.find { it.selected }
                val stageId = stage?.id
                val publish = stage?.publishers?.find { it.selected }
                val publishId = publish?.id
                val semester = publish?.semesters?.find { it.selected}
                val semesterId = semester?.id
                val string = "$stageId-$publishId-$semesterId"
                Toast.makeText(requireContext(), "选中了${string}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getScreenHeight(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }
}