package com.yangcong345.kratos.resourse

import com.yangcong345.context_provider.ContextProvider
import com.yangcong345.platform.utils.EncryptUtil
import java.io.File
import java.io.InputStream

/**
 * 时间：2022/3/10 4:17 下午
 * 作者：吕卡
 * 备注：用于添加、删除、查找DSL和JS的本地缓存
 */

object LocalHelper {

    private const val FILE_DSL = "dsl"
    private const val FILE_JS = "js"

    /**
     * 添加到缓存中
     */
    fun saveCacheSource(source: PageSource, dslStream: InputStream, jsStream: InputStream) {
        saveDslFile(source.dsl, dslStream)
        saveJSFile(source.code, jsStream)
    }

    /**
     * 从缓存中获取
     */
    fun getCacheSourceBundle(source: PageSource): DSLBundle? {
        val pageName = source.pageName
        val dslFile = getDslFile(source.dsl)
        val jsFile = getJSFile(source.code)
        if (dslFile.exists() && jsFile.exists()) {
            return DSLBundle(pageName, dslFile, listOf(jsFile))
        }
        return null
    }

    private fun getDslFile(dsl: Dsl): File {
        val version = dsl.version
        val md5 = dsl.md5
        val fileName = EncryptUtil.encryptMD5ToString(dsl.url)
        val path = FILE_DSL + File.separator + version + File.separator + md5 + File.separator + fileName
        return File(ContextProvider.getApplication().getExternalFilesDir(null), path)
    }

    private fun getJSFile(code: Code): File {
        val version = code.version
        val md5 = code.md5
        val fileName = EncryptUtil.encryptMD5ToString(code.url)
        val path = FILE_JS + File.separator + version + File.separator + md5 + File.separator + fileName
        return File(ContextProvider.getApplication().getExternalFilesDir(null), path)
    }

    private fun saveDslFile(dsl: Dsl, byteStream: InputStream): Boolean {
        val version = dsl.version
        val md5 = dsl.md5
        val fileName = EncryptUtil.encryptMD5ToString(dsl.url)
        val path = FILE_DSL + File.separator + version + File.separator + md5 + File.separator + fileName
        val file = File(ContextProvider.getApplication().getExternalFilesDir(null), path)
        val outputStream = file.outputStream()
        return try {
            byteStream.buffered().copyTo(outputStream)
            byteStream.close()
            outputStream.flush()
            outputStream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun saveJSFile(jsCode: Code, byteStream: InputStream): Boolean {
        val version = jsCode.version
        val md5 = jsCode.md5
        val fileName = EncryptUtil.encryptMD5ToString(jsCode.url)
        val path = FILE_JS + File.separator + version + File.separator + md5 + File.separator + fileName
        val file = File(ContextProvider.getApplication().getExternalFilesDir(null), path)
        val outputStream = file.outputStream()
        return try {
            byteStream.buffered().copyTo(outputStream)
            byteStream.close()
            outputStream.flush()
            outputStream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}