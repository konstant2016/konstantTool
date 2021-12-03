package com.konstant.tool.lite.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.KonApplication
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * 时间：2018/7/30 10:53
 * 作者：吕卡
 * 描述：
 *
 * 将文件保存到APP安装目录下的缓存中
 * 从安卓整洁度考虑，不要把缓存文件放到公共路径下，
 * 说实话，我很鄙视这种在SD卡根目录建立文件夹的行为
 * 如果要长久保存，则放在getExternalFilesDir(null)中
 * 如果是作为缓存，则放在getExternalCacheDir()中
 * 保存到私有存储的话，不需要申请额外权限
 * <p>
 * context.getExternalFilesDir(null)————/storage/emulated/0/Android/包名/files
 * <p>
 * context.getExternalCacheDir()————————/storage/emulated/0/Android/包名/cache
 * <p>
 * context.getFilesDir()————————————————/data/data/包名/files
 * <p>
 * context.getCacheDir()————————————————/data/data/包名/cache
 * <p>
 * Environment.getExternalStoragePublicDirectory(null)————手机内部存储根目录，需要申请读写权限
 * Environment.getExternalStorageDirectory()——————————————SD卡(如果有)根目录，需要申请读写权限
 * <p>
 * 注意：调用以上接口保存的数据，在APP下载之后，数据会随之删除，不留垃圾
 * 注意：data 分区十分有限，不建议把大型数据保存在 data 分区下
 * 注意：Google建议把数据保存在/storage/emulated/0/Android/包名/files下
 * 注意：上面方法中需要填写参数的接口，内部的参数可以指定子文件夹，参数可以放Environment.DIRECTORY_PICTURES之类的
 * 比如context.getExternalFilesDir(Environment.DIRECTORY_DCIM)
 *
 * @param fileName ： 保存之后的文件名字
 * @param bytes    ： 要保存的文件的字节流
 * @return
 */

object FileUtil {

    private const val SHARED_PREFERENCE_FILE_NAME = "information"


    // 保存文件到缓存目录下
    fun saveFileToCache(context: Context, fileName: String, bytes: ByteArray): Boolean =
            saveFile(File(context.externalCacheDir, fileName), bytes)


    // 保存文件到安装目录下
    fun saveFileToFile(context: Context, fileName: String, bytes: ByteArray): Boolean =
            saveFile(File(context.getExternalFilesDir(null), fileName), bytes)


    // 从缓存目录下读取文件
    fun readFileFromCache(context: Context, fileName: String): ByteArray =
            readFile(File(context.externalCacheDir, fileName))


    // 从安装目录下读取文件
    fun readFileFromFile(context: Context, fileName: String): ByteArray =
            readFile(File(context.getExternalFilesDir(null), fileName))

    // 保存图片到相册
    fun saveBitmapToAlbum(bytes: ByteArray? = null, bitmap: Bitmap? = null, name: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            saveBitmapToAlbumQ(bytes, bitmap, name)
        else
            saveBitmapToAlbumP(bytes, bitmap, name)
    }

    private fun saveBitmap(bitmap: Bitmap,name: String){
        MediaStore.Images.Media.insertImage(KonApplication.context.contentResolver,bitmap,name,"")
    }

    private fun saveBitmapToAlbumP(bytes: ByteArray? = null, bitmap: Bitmap? = null, name: String): Boolean {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(directory, name)
        if (bytes != null) {
            val result = saveFile(file, bytes)
            if (result) {
                sendScanBroadCast(file)
            }
            return result
        }
        if (bitmap != null) {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val result = saveFile(file, stream.toByteArray())
            if (result) {
                sendScanBroadCast(file)
            }
            return result
        }
        return false
    }

    private fun sendScanBroadCast(file: File) {
        with(Intent()) {
            action = Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
            data = Uri.fromFile(file)
            KonApplication.context.sendBroadcast(this)
        }
    }

    private fun saveBitmapToAlbumQ(bytes: ByteArray? = null, bitmap: Bitmap? = null, name: String): Boolean {
        try {
            with(ContentValues()) {
                put(MediaStore.Images.Media.DISPLAY_NAME, name)
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.TITLE, name)
                val uri = KonApplication.context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this)
                        ?: return false
                val stream = KonApplication.context.contentResolver.openOutputStream(uri)
                        ?: return false
                if (bytes != null) stream.write(bytes)
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()
                return true
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            return false
        }
    }

    // 保存数据到SharedPreference
    fun saveDataToSp(context: Context, key: String, value: Any): Boolean {
        val editor = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE).edit()
        when (value) {
            is Int ->
                editor.putInt(key, value)
            is Boolean ->
                editor.putBoolean(key, value)
            is String ->
                editor.putString(key, value)
            is Float ->
                editor.putFloat(key, value)
            is Long ->
                editor.putLong(key, value)
        }
        return editor.commit()
    }


    // 从SharedPreference读取数据
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> readDataFromSp(context: Context, key: String, default: T): T =
            with(context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)) {
                val response: Any = when (default) {
                    is Long -> getLong(key, default)
                    is Int -> getInt(key, default)
                    is Boolean -> getBoolean(key, default)
                    is Float -> getFloat(key, default)
                    is String -> getString(key, "")!!
                    else -> throw IllegalArgumentException("SharedPreference不支持此类型的存储！")
                }
                response as T
            }

    private fun saveFile(file: File, bytes: ByteArray): Boolean {
        if (file.exists()) file.delete()
        return try {
            file.writeBytes(bytes);true
        } catch (e: Exception) {
            false
        }
    }

    private fun readFile(file: File): ByteArray {
        if (!file.exists()) file.createNewFile()
        return file.readBytes()
    }

    // 安卓7.0以上用的到这个
    fun createUriWithFile(context: Context, fileName: String): Uri {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
        return FileProvider.getUriForFile(context, context.packageName + ".provider", file)
    }

    // 根据名字拿图片
    fun getPrivateBitmap(context: Context, fileName: String): Bitmap? {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }
}