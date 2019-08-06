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
import com.konstant.tool.lite.view.Adapter
import com.konstant.tool.lite.view.KonstantDialog
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_dialog_header_selector.view.*
import kotlinx.android.synthetic.main.pop_item_setting.view.*
import org.greenrobot.eventbus.EventBus


/**
 * 描述:APP设置
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class SettingActivity : BaseActivity() {

    private val swipeStateList = listOf("全部关闭", "从左向右", "从右向左", "从下向上", "全部启用")
    private val browserTypeList = listOf("默认设置", "Chrome Browser", "QQ浏览器", "UC浏览器", "系统浏览器")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setTitle("设置")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        setViewsStatus()
    }

    private fun setViewsStatus() {
        // 主题设置
        layout_theme.setOnClickListener { startActivity(ThemeActivity::class.java) }

        // 头像设置
        layout_header.setOnClickListener { headerSelector() }

        // 退出提示
        switch_exit.isChecked = SettingManager.getExitTipsStatus(this)
        switch_exit.setOnCheckedChangeListener { _, isChecked -> SettingManager.saveExitTipsStatus(this, isChecked) }
        layout_exit.setOnClickListener { switch_exit.isChecked = !switch_exit.isChecked }

        // 杀进程
        switch_kill.isChecked = SettingManager.getKillProcess(this)
        switch_kill.setOnCheckedChangeListener { _, isChecked -> SettingManager.saveKillProcess(this, isChecked) }
        layout_kill.setOnClickListener { switch_kill.isChecked = !switch_kill.isChecked }

        // 滑动返回
        layout_swipe.setOnClickListener { onSwipeBackClick() }
        layout_swipe.setHintText(swipeStateList[SettingManager.getSwipeBackStatus(this)])

        // 浏览器类型
        layout_browser.setOnClickListener { onBrowserClick() }
        layout_browser.setHintText(browserTypeList[SettingManager.getBrowserType(this)])

        // 适配系统暗黑主题
        switch_dark.isChecked = SettingManager.getAdapterDarkMode(this)
        switch_dark.setOnCheckedChangeListener { _, isChecked ->
            SettingManager.setAdapterDarkMode(this, isChecked)
            if (SettingManager.getDarkModeStatus(this))
                EventBus.getDefault().post(ThemeChanged())
        }
        layout_dark.setOnClickListener { switch_dark.isChecked = !switch_dark.isChecked }

        // 关于
        layout_about.setOnClickListener { startActivity(AboutActivity::class.java) }
    }

    // 点击滑动返回状态后的操作
    private fun onSwipeBackClick() {
        val view = layoutInflater.inflate(R.layout.pop_item_setting, null)
        val dialog = KonstantDialog(this)
                .addView(view)
                .hideNavigation()
                .createDialog()
        view.layout_list_view.adapter = Adapter(this, swipeStateList)
        view.layout_list_view.setOnItemClickListener { _, _, position, _ ->
            dialog.dismiss()
            SettingManager.setSwipeBackStatus(this, position)
            EventBus.getDefault().post(SwipeBackStatus(position))
            layout_swipe.setHintText(swipeStateList[SettingManager.getSwipeBackStatus(this)])
        }
    }

    // 浏览器类型点击后的操作
    private fun onBrowserClick() {
        val view = layoutInflater.inflate(R.layout.pop_item_setting, null)
        val dialog = KonstantDialog(this)
                .addView(view)
                .hideNavigation()
                .createDialog()
        view.layout_list_view.adapter = Adapter(this, browserTypeList)
        view.layout_list_view.setOnItemClickListener { _, _, position, _ ->
            dialog.dismiss()
            SettingManager.saveBrowserType(this, position)
            layout_browser.setHintText(browserTypeList[SettingManager.getBrowserType(this)])
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
