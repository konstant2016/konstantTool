package com.konstant.konstanttools.ui.activity

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseActivity
import kotlinx.android.synthetic.main.activity_smsfake.*
import java.text.SimpleDateFormat
import java.util.*

class SMSFakeActivity : BaseActivity() {

    private lateinit var mDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smsfake)
        setTitle("短信伪造")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        layout_sms_bg.setOnClickListener { hideSoftKeyboard() }

        // 发送时间点击后
        tv_time.setOnClickListener {
            hideSoftKeyboard()
            layout_picker.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_bottom_to_top))
            layout_picker.visibility = View.VISIBLE
        }

        // 取消按钮点击后
        btn_cancel.setOnClickListener {
            layout_picker.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_top_to_bottom))
            layout_picker.visibility = View.GONE
        }

        // 确定按钮点击后
        btn_confirm.setOnClickListener {
            mDate = Date(picker_date.year - 1900, picker_date.month, picker_date.dayOfMonth + 1, picker_time.hour, picker_time.minute)
            val s = SimpleDateFormat("yyyy年MM月dd日HH:mm").format(mDate)
            tv_time.text = s
            layout_picker.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_top_to_bottom))
            layout_picker.visibility = View.GONE
        }

        // 伪造按钮点击后
        btn_create.setOnClickListener {
            if (TextUtils.isEmpty(et_content.text)) {
                Toast.makeText(this, "短信内容没填", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_sender.text)) {
                Toast.makeText(this, "发送者没填", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(tv_time.text)) {
                Toast.makeText(this, "发送时间没填", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            requestPermission(Manifest.permission.SEND_SMS, "需要短信权限用以写入伪造的短信")
        }
    }

    override fun onPermissionResult(result: Boolean) {
        super.onPermissionResult(result)
        if (result) {
            writeSMS(mDate.time, et_sender.text.toString(), et_content.text.toString())
        } else {
            Toast.makeText(this, "没有短信权限", Toast.LENGTH_SHORT).show()
        }
    }

    // 写入到系统库
    private fun writeSMS(date: Long, address: String, body: String) {
        val values = ContentValues()
        values.put("address", address)
        values.put("type", 1)
        values.put("date", date)
        values.put("body", body)
        contentResolver.insert(Uri.parse("content://sms"), values)
        Toast.makeText(this, "伪造成功，去系统收件箱查看吧", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (layout_picker.visibility == View.VISIBLE) {
            layout_picker.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_top_to_bottom))
            layout_picker.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }
}
