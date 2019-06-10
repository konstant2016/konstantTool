package com.konstant.tool.lite.module.file

import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_file_clean.*
import java.io.File

class FileCleanActivity : BaseActivity() {

    private val fileList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_clean)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        btn_start.setOnClickListener { scanRootFile() }
    }

    private fun scanRootFile() {
        AndPermission.with(this)
                .permission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .onGranted { scanFile() }
                .onDenied { showToast("") }
                .start()
    }

    private fun scanFile(){
        fileList.clear()
        Thread {
            scanFile(Environment.getExternalStorageDirectory());
            Log.d("FileCleanActivity","扫描完了")
        }.start()
    }

    private fun scanFile(file: File){
        val index = file.path.lastIndexOf(File.separator)
        val name = file.path.substring(index)
        if (name == "Android") return
        if (file.isDirectory) {
            if (file.listFiles().isEmpty()) {
                fileList.add(file.path)
                runOnUiThread {
                    tv_scan.text = "正在扫描：${file.path}"
                    tv_state.text = "已扫描：${fileList.size}"
                }
            } else {
                file.listFiles().forEach { scanFile(it) }
            }
        }
    }

    private fun cleanFile() {

    }
}
