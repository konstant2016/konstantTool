package com.konstant.tool.lite.module.setting.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.*
import com.konstant.tool.lite.module.express.ExpressManager
import com.konstant.tool.lite.module.extract.AppData
import com.konstant.tool.lite.module.extract.PackagePresenter
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.module.skip.AutoSkipManager
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
        layout_exit.setChecked(SettingManager.getExitTipsStatus(this))
        layout_exit.setOnCheckedChangeListener { SettingManager.saveExitTipsStatus(this, it) }

        // 杀进程
        layout_kill.setChecked(SettingManager.getKillProcess(this))
        layout_kill.setOnCheckedChangeListener { SettingManager.saveKillProcess(this, it) }

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
        layout_update.setChecked(SettingManager.getAutoCheckUpdate(this))
        layout_update.setOnCheckedChangeListener { SettingManager.saveAutoCheckUpdate(this, it) }

        // 网页版快递查询
        layout_express.setChecked(SettingManager.getExpressWithHtml(this))
        layout_express.setOnCheckedChangeListener { SettingManager.saveExpressWithHtml(this, it) }

        // 显示收藏列表
        layout_collect.setChecked(SettingManager.getShowCollection(this))
        layout_collect.setOnCheckedChangeListener {
            SettingManager.saveShowCollection(this, it)
            EventBus.getDefault().post(CollectionSettingChanged())
        }

        // 适配系统暗黑主题
        layout_dark.setChecked(SettingManager.getAdapterDarkMode(this))
        layout_dark.setOnCheckedChangeListener {
            SettingManager.setAdapterDarkMode(this, it)
            if (SettingManager.getDarkModeStatus(this))
                EventBus.getDefault().post(ThemeChanged())
        }

        // 强制全局缩放
        layout_scale.setChecked(SettingManager.getViewScale(this))
        layout_scale.setOnCheckedChangeListener {
            SettingManager.saveViewScale(this,it)
            EventBus.getDefault().post(ViewScaleChanged())
        }

        layout_reset.setOnClickListener { onResetClick() }

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

    // 点击重置所有提示
    private fun onResetClick() {
        KonstantDialog(this)
                .setMessage("${getString(R.string.setting_reset_tips)}?")
                .setPositiveListener {
                    ExpressManager.setShowDialog(this, true)
                    AutoSkipManager.setShowDialogTips(this, true)
                    it.dismiss()
                    showToast(getString(R.string.base_txt_success))
                }
                .createDialog()
    }

    private fun shareApp() {
        val appData = AppData(packageName, getDrawable(R.drawable.ic_launcher)!!, getString(R.string.app_name))
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
