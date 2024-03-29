package com.konstant.tool.lite.base

import android.text.TextUtils
import com.konstant.tool.lite.R
import com.konstant.tool.lite.network.NetworkHelper
import com.konstant.tool.lite.util.AppUtil
import com.konstant.tool.lite.view.KonstantDialog

object UpdateManager {

    /**
     * status       检查更新状态：网络错误、服务器没有正确返回等，会走到这里
     * newVersion   是否有新版本
     * versionName  新版本的版本号
     * describe     更新描述信息
     * url          下载链接
     */
    private fun checkUpdate(updateResult: (status: Boolean, newVersion: Boolean, versionName: String, describe: String, url: String) -> Unit) {
        val disposable = NetworkHelper.getUpdate()
                .subscribe({ response ->
                    if (response.versionCode == 0 || TextUtils.isEmpty(response.versionName)) {
                        updateResult.invoke(false, false, "", "", "")
                        return@subscribe
                    }
                    val info = KonApplication.context.packageManager.getPackageInfo(KonApplication.context.packageName, 0)
                    if (response.versionCode <= info.versionCode) {
                        updateResult.invoke(true, false, "", "", "")
                        return@subscribe
                    }
                    updateResult.invoke(true, true, response.versionName, response.updateDescribe, response.downloadUrl)

                }, {
                    updateResult.invoke(false, false, "", "", "")
                    it.printStackTrace()
                })
    }

    // 自动检查更新
    fun autoCheckoutUpdate() {
        checkUpdate { status, newVersion, versionName, describe, url ->
            if (!status) return@checkUpdate
            if (!newVersion) return@checkUpdate
            showUpdateDialog(versionName, describe, url)
        }
    }

    // 手动检查更新
    fun checkoutUpdate() {
        checkUpdate { status, newVersion, versionName, describe, url ->
            if (!status) {
                AppUtil.getTopActivity()?.showToast(KonApplication.context.getString(R.string.update_manager_check_update_error))
                return@checkUpdate
            }
            if (!newVersion) {
                AppUtil.getTopActivity()?.showToast(KonApplication.context.getString(R.string.update_manager_new_version_already))
                return@checkUpdate
            }
            showUpdateDialog(versionName, describe, url)
        }
    }

    // 获取更新链接
    fun getUpdateUrl(result: (String) -> Unit) {
        val disposable = NetworkHelper.getUpdate()
                .subscribe({
                    result.invoke(it.downloadUrl)
                }, {
                    result.invoke("http://www.pgyer.com/r6L4")
                })
    }


    // 展示更新提示框,用户点击确认后跳转到浏览器进行下载
    private fun showUpdateDialog(versionName: String, describe: String, url: String) {
        AppUtil.getTopActivity()?.let { activity ->
            activity.runOnUiThread {
                KonstantDialog(activity)
                        .setTitle("${KonApplication.context.getString(R.string.update_manager_new_version)}：$versionName")
                        .setMessage(describe)
                        .setPositiveListener {
                            H5Activity.openWebView(activity, url, true)
                        }
                        .createDialog()
            }

        }
    }
}