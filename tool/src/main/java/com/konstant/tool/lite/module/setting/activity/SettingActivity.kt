package com.konstant.tool.lite.module.setting.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.*
import com.konstant.tool.lite.module.extract.AppData
import com.konstant.tool.lite.module.extract.PackagePresenter
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.util.ImageSelector
import com.konstant.tool.lite.util.PermissionRequester
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_setting.*
import org.greenrobot.eventbus.EventBus
import java.io.File

/**
 * 描述:APP设置
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class SettingActivity : BaseActivity() {

    private val mLanguageList by lazy { listOf(getString(R.string.setting_language_item_system), "简体中文", "ENGLISH") }
    private val mSwipeStateList by lazy { listOf(getString(R.string.setting_swipe_item_close), getString(R.string.setting_swipe_item_left), getString(R.string.setting_swipe_item_right), getString(R.string.setting_swipe_item_bottom), getString(R.string.setting_swipe_item_open)) }
    private val mBrowserTypeList by lazy { listOf(getString(R.string.setting_browser_item_default), "Chrome Browser", getString(R.string.setting_browser_item_QQ), getString(R.string.setting_browser_item_UC), getString(R.string.setting_browser_item_system)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setTitle(getString(R.string.setting_title))
        initViews()
    }

    private fun initViews() {
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
        layout_swipe.setHintText(mSwipeStateList[SettingManager.getSwipeBackStatus(this)])

        // 语言设置
        layout_language.setOnClickListener { onLanguageClick() }
        layout_language.setHintText(mLanguageList[SettingManager.getDefaultLanguage(this)])

        // 浏览器类型
        layout_browser.setOnClickListener { onBrowserClick() }
        layout_browser.setHintText(mBrowserTypeList[SettingManager.getBrowserType(this)])

        // 自动检查更新
        switch_update.isChecked = SettingManager.getAutoCheckUpdate(this)
        switch_update.setOnCheckedChangeListener { _, isChecked -> SettingManager.saveAutoCheckUpdate(this, isChecked) }
        layout_update.setOnClickListener { switch_update.isChecked = !switch_update.isChecked }

        // 网页版快递查询
        switch_express.isChecked = SettingManager.getExpressWithHtml(this)
        switch_express.setOnCheckedChangeListener { _, isChecked -> SettingManager.saveExpressWithHtml(this, isChecked) }
        layout_express.setOnClickListener { switch_express.isChecked = !switch_express.isChecked }

        // 显示收藏列表
        switch_collect.isChecked = SettingManager.getShowCollection(this)
        switch_collect.setOnCheckedChangeListener { _, isChecked ->
            SettingManager.saveShowCollection(this, isChecked)
            EventBus.getDefault().post(CollectionSettingChanged())
        }
        layout_collect.setOnClickListener {
            switch_collect.isChecked = !switch_collect.isChecked
        }

        // 适配系统暗黑主题
        switch_dark.isChecked = SettingManager.getAdapterDarkMode(this)
        switch_dark.setOnCheckedChangeListener { _, isChecked ->
            SettingManager.setAdapterDarkMode(this, isChecked)
            if (SettingManager.getDarkModeStatus(this))
                EventBus.getDefault().post(ThemeChanged())
        }
        layout_dark.setOnClickListener { switch_dark.isChecked = !switch_dark.isChecked }

        // 分享应用
        layout_share.setOnClickListener { shareApp() }

        // 关于
        layout_about.setOnClickListener { startActivity(AboutActivity::class.java) }
    }

    // 点击滑动返回状态后的操作
    private fun onSwipeBackClick() {
        KonstantDialog(this)
                .hideNavigation()
                .setItemList(mSwipeStateList)
                .setOnItemClickListener { dialog, position ->
                    dialog.dismiss()
                    SettingManager.setSwipeBackStatus(this, position)
                    EventBus.getDefault().post(SwipeBackStatus(position))
                    layout_swipe.setHintText(mSwipeStateList[SettingManager.getSwipeBackStatus(this)])
                }
                .createDialog()
    }

    // 点击选择语言后的操作
    private fun onLanguageClick() {
        KonstantDialog(this)
                .hideNavigation()
                .setItemList(mLanguageList)
                .setOnItemClickListener { dialog, position ->
                    dialog.dismiss()
                    if (SettingManager.getDefaultLanguage(this) == position)
                        return@setOnItemClickListener
                    SettingManager.saveDefaultLanguage(this, position)
                    EventBus.getDefault().post(LanguageChanged())
                    layout_language.setHintText(mLanguageList[SettingManager.getDefaultLanguage(this)])
                }
                .createDialog()
    }

    // 浏览器类型点击后的操作
    private fun onBrowserClick() {
        KonstantDialog(this)
                .hideNavigation()
                .setItemList(mBrowserTypeList)
                .setOnItemClickListener { dialog, position ->
                    dialog.dismiss()
                    SettingManager.saveBrowserType(this, position)
                    layout_browser.setHintText(mBrowserTypeList[SettingManager.getBrowserType(this)])
                }
                .createDialog()
    }

    // 头像选择
    private fun headerSelector() {
        KonstantDialog(this)
                .hideNavigation()
                .setItemList(listOf(getString(R.string.setting_header_item_camera), getString(R.string.setting_header_item_photo), getString(R.string.setting_header_item_recover)))
                .setOnItemClickListener { dialog, position ->
                    dialog.dismiss()
                    when (position) {
                        // 拍照
                        0 -> {
                            PermissionRequester.requestPermission(this,
                                    mutableListOf(Manifest.permission.CAMERA),
                                    {
                                        ImageSelector.takePhoto(this, SettingManager.NAME_USER_HEADER) {
                                            if (it) {
                                                EventBus.getDefault().post(UserHeaderChanged())
                                                showToast(getString(R.string.setting_header_success))
                                            }
                                        }
                                    },
                                    { showToast(getString(R.string.setting_header_permission_cancel)) })
                        }
                        // 相册
                        1 -> {
                            ImageSelector.selectImg(this, SettingManager.NAME_USER_HEADER) {
                                if (it) {
                                    EventBus.getDefault().post(UserHeaderChanged())
                                    showToast(getString(R.string.setting_header_success))
                                }
                            }
                        }
                        // 恢复默认
                        2 -> {
                            dialog.dismiss()
                            SettingManager.deleteUserHeaderThumb(this)
                            EventBus.getDefault().post(UserHeaderChanged())
                            showToast(getString(R.string.setting_header_recover_success))
                        }
                    }
                }
                .createDialog()
    }

    private fun shareApp() {
        val appData = AppData(packageName,getDrawable(R.drawable.ic_launcher)!!,getString(R.string.app_name))
        val path = getExternalFilesDir(null)?.path + File.separator + "apks"
        PackagePresenter.backApp(path, appData) { status, file ->
            runOnUiThread {
                val fileUri = FileProvider.getUriForFile(this, "${packageName}.provider", file)
                val intent = ShareCompat.IntentBuilder.from(this)
                        .setType("application/apk")
                        .intent
                intent.putExtra(Intent.EXTRA_STREAM, fileUri)
                startActivity(intent)
            }
        }
    }
}
