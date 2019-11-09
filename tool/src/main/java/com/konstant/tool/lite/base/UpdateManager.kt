package com.konstant.tool.lite.base

import android.content.Intent
import android.text.TextUtils
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
                AppUtil.getTopActivity()?.showToast("检查失败，可能是网络或者服务器挂了")
                return@checkUpdate
            }
            if (!newVersion) {
                AppUtil.getTopActivity()?.showToast("当前已经是最新版本")
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
                        .setTitle("新版本：$versionName")
                        .setMessage(describe)
                        .setPositiveListener {
                            with(Intent(activity, H5Activity::class.java)) {
                                putExtra(H5Activity.H5_URL, url)
                                putExtra(H5Activity.H5_BROWSER, true)
                                activity.startActivity(this)
                            }
                        }
                        .createDialog()
            }

        }
    }
}