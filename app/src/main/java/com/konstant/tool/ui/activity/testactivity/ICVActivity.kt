package com.konstant.tool.ui.activity.testactivity

import android.os.Bundle
import android.util.Log
import com.konstant.tool.R
import com.konstant.tool.base.BaseActivity
import kotlinx.android.synthetic.main.activity_icv.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest
import java.util.zip.ZipFile

class ICVActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icv)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        btn_checkout_dex.setOnClickListener { checkoutDex() }
        btn_checkout_apk.setOnClickListener { checkoutAPK() }
    }

    // dex校验
    private fun checkoutDex() {
        val dexCrc = getString(R.string.classes_dex_crc).toLong()

        try {
            val zipFile = ZipFile(packageCodePath)
            val entry = zipFile.getEntry("classes.dex")
            Log.i("verification", "classes.dexcrc=" + entry.crc)
            if (entry.crc != dexCrc) {
                tv_state.text = "dex验证失败"
            } else {
                tv_state.text = "dex验证成功"
            }
        } catch (io: IOException) {
            tv_state.text = "dex验证崩溃${io.toString()}"
        }
    }

    // apk校验
    private fun checkoutAPK() {
        val originSHA = "901e976d6b6776b3748566cb32f51c283d3cd1c3"
        val apkPath = packageCodePath
        try {
            val msgDigest = MessageDigest.getInstance("SHA-1")
            val bytes = ByteArray(1024)
            var len: Int = 0
            val fis = FileInputStream(File(apkPath))
            while (len != -1) {
                len = fis.read(bytes)
                if (len != -1) {
                    msgDigest.update(bytes, 0, len)
                }
            }
            val bi = BigInteger(1, msgDigest!!.digest())
            val sha = bi.toString(16)
            fis.close()
            Log.i("sha",sha)
            if(originSHA != sha){
                tv_state.text = "apk验证失败"
            } else {
                tv_state.text = "apk验证成功"
            }
            //这里添加从服务器中获取哈希值然后进行对比校验
        } catch (e: Exception) {
            e.printStackTrace()
            tv_state.text = "apk验证失败"
        }
    }
}
