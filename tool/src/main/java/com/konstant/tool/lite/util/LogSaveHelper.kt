package com.konstant.tool.lite.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.nio.charset.Charset

object LogSaveHelper {

    fun saveLog(string: String, context: Context) {
        val file = File(Environment.getExternalStorageDirectory(),"konstantLog.json")
        val toByteArray = string.toByteArray(Charset.forName("UTF-8"))
        file.writeBytes(toByteArray)
    }

}