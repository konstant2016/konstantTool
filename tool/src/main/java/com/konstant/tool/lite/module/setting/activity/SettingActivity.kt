package com.konstant.tool.lite.module.setting.activity

import android.Manifest
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.module.setting.param.SwipeBackStatus
import com.konstant.tool.lite.module.setting.param.ThemeChanged
import com.konstant.tool.lite.module.setting.param.UserHeaderChanged
import com.konstant.tool.lite.util.ImageSelector
import com.konstant.tool.lite.view.KonstantDialog
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_dialog_header_selector.view.*
import kotlinx.android.synthetic.main.pop_swipe_back.view.*
import org.greenrobot.eventbus.EventBus


/**
 * 描述:APP设置
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setTitle("设置")
        initBaseViews()
    }

    override fun onResume() {
        super.onResume()
        setViewsStatus()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        setViewsStatus()
    }

    private fun setViewsStatus() {
        layout_theme.setOnClickListener { startActivity(ThemeActivity::class.java) }

        // 滑动返回
        layout_swipe.setOnClickListener { onSwipeBackClick() }
        setSwipeTxt()

        // 退出提示
        btn_exit.isChecked = SettingManager.getExitTipsStatus(this)
        btn_exit.setOnCheckedChangeListener { _, isChecked ->
            SettingManager.setExitTipsStatus(this, isChecked)
        }
        layout_exit.setOnClickListener {
            btn_exit.isChecked = !btn_exit.isChecked
        }

        // 杀进程
        btn_kill.isChecked = SettingManager.getKillProcess(this)
        btn_kill.setOnCheckedChangeListener { _, isChecked ->
            SettingManager.setKillProcess(this, isChecked)
        }
        layout_kill.setOnClickListener {
            btn_kill.isChecked = !btn_kill.isChecked
        }

        // 适配系统暗黑主题
        btn_dark.isChecked = SettingManager.getAdapterDarkMode(this)
        btn_dark.setOnCheckedChangeListener { _, isChecked ->
            SettingManager.setAdapterDarkMode(this, isChecked)
            if (SettingManager.getDarkModeStatus(this))
                EventBus.getDefault().post(ThemeChanged())
        }
        layout_dark.setOnClickListener {
            btn_dark.isChecked = !btn_dark.isChecked
        }

        layout_about.setOnClickListener { startActivity(AboutActivity::class.java) }

        layout_header.setOnClickListener { headerSelector() }
    }

    // 点击滑动返回状态后的操作
    private fun onSwipeBackClick() {
        val view = layoutInflater.inflate(R.layout.pop_swipe_back, null)
        val dialog = KonstantDialog(this)
                .addView(view)
                .hideNavigation()
                .createDialog()
        view.tv_back_none.setOnClickListener { onSwipeItemClick(0, dialog) }
        view.tv_back_left.setOnClickListener { onSwipeItemClick(1, dialog) }
        view.tv_back_right.setOnClickListener { onSwipeItemClick(2, dialog) }
        view.tv_back_bottom.setOnClickListener { onSwipeItemClick(3, dialog) }
        view.tv_back_all.setOnClickListener { onSwipeItemClick(4, dialog) }
    }

    // 点击某一选项后的操作
    private fun onSwipeItemClick(index: Int, dialog: KonstantDialog) {
        dialog.dismiss()
        SettingManager.setSwipeBackStatus(this, index)
        EventBus.getDefault().post(SwipeBackStatus(index))
        setSwipeTxt()
    }

    // 更新滑动返回状态提示语
    private fun setSwipeTxt() {
        when (SettingManager.getSwipeBackStatus(this)) {
            0 -> {
                tv_swipe_back_status.text = "全部关闭"
            }
            1 -> {
                tv_swipe_back_status.text = "从左向右"
            }
            2 -> {
                tv_swipe_back_status.text = "从右向左"
            }
            3 -> {
                tv_swipe_back_status.text = "从下向上"
            }
            4 -> {
                tv_swipe_back_status.text = "全部启用"
            }
        }
    }

    // 头像选择
    private fun headerSelector() {
        val dialog = KonstantDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_dialog_header_selector, null)
        // 拍照
        view.text_camera.setOnClickListener {
            dialog.dismiss()
            AndPermission.with(this)
                    .permission(Manifest.permission.CAMERA)
                    .onGranted {
                        ImageSelector.takePhoto(this, SettingManager.NAME_USER_HEADER) {
                            if (it) {
                                EventBus.getDefault().post(UserHeaderChanged())
                                showToast("设置成功")
                            }
                        }
                    }
                    .onDenied { showToast("您拒绝了相机权限") }
                    .start()
        }
        // 相册
        view.text_photo.setOnClickListener {
            dialog.dismiss()
            ImageSelector.selectImg(this, SettingManager.NAME_USER_HEADER) {
                if (it) {
                    EventBus.getDefault().post(UserHeaderChanged())
                    showToast("设置成功")
                }
            }
        }
        // 恢复默认
        view.text_default.setOnClickListener {
            dialog.dismiss()
            SettingManager.deleteUserHeaderThumb(this)
            EventBus.getDefault().post(UserHeaderChanged())
            showToast("已恢复默认")
        }
        // 显示dialog
        dialog.hideNavigation()
                .addView(view)
                .createDialog()
    }
}
