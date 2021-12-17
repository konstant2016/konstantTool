//通过@file: JvmName("ContextUtil")注解，将生成的类名修改为ContextUtil，并且调用的时候直接调用ContextUtil.foo()即可
//放在文件顶部，在package声明的前面
@file: JvmName("ContextUtil")

package com.konstant.develop

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


/**
 * @author: drawf
 * @date: 2018/8/24
 * @see: <a href=""></a>
 */
//********************************
//* Toast相关
//********************************

//********************************
//* 尺寸转换
//********************************

fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}

fun Float.toPx(): Int {
    val scale: Float = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.toPx(): Int {
    val scale: Float = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.toDp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

fun Float.toDp(): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return (this / scale + 0.5f)
}


//----------屏幕尺寸----------


fun Context.getScreenWidth(): Int {
    var wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.x
}

fun Context.getScreenHeight(): Int {
    var wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.y
}

/**
 * 获取状态栏高度
 *
 * @return 高度
 */

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resId = this.resources.getIdentifier("status_bar_height", "dimen", "android");
    if (resId > 0) {
        result = Resources.getSystem().getDimensionPixelSize(resId)
    }
    return result;
}

fun getNavigationBarHeight(): Int {
    var result = 0
    val resId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android");
    if (resId > 0) {
        result = Resources.getSystem().getDimensionPixelSize(resId)
    }
    return result;
}

/**
 * Return true if this [Context] is available.
 * Availability is defined as the following:
 * + [Context] is not null
 * + [Context] is not destroyed (tested with [FragmentActivity.isDestroyed] or [Activity.isDestroyed])
 */
fun Context?.isAvailable(): Boolean {
    if (this == null) {
        return false
    } else if (this !is Application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (this is androidx.fragment.app.FragmentActivity) {
                return !this.isDestroyed
            } else if (this is Activity) {
                return !this.isDestroyed
            }
        }
    }
    return true
}



fun Context.layoutInflater(): LayoutInflater {
    return LayoutInflater.from(this)
}

fun Context.inflate(@LayoutRes res: Int, container: ViewGroup, attachToRoot: Boolean): View {
    return LayoutInflater.from(this).inflate(res, container, attachToRoot)
}

fun ViewGroup.inflate(@LayoutRes res: Int, attachToRoot: Boolean): View {
    return this.context.inflate(res, this, attachToRoot)
}

fun Context.inflate(@LayoutRes res: Int): View {
    return LayoutInflater.from(this).inflate(res, null)
}

val typefaceMap = hashMapOf<String, Typeface?>()

fun FragmentActivity.replaceFragment(@IdRes containerViewId: Int, fragment: Fragment, tag: String) {
    if (this.isFinishing) return
    supportFragmentManager
        .beginTransaction()
        .replace(containerViewId, fragment, tag)
        .commitAllowingStateLoss()
}

fun FragmentActivity.preLoadFragment(@IdRes containerViewId: Int, fragment: Fragment, tag: String) {
    if (this.isFinishing) return
    supportFragmentManager
        .beginTransaction()
        .replace(containerViewId, fragment, tag)
        .hide(fragment)
        .commitAllowingStateLoss()
}

fun FragmentActivity.addFragment(@IdRes containerViewId: Int, fragment: Fragment, tag: String) {
    if (this.isFinishing) return
    supportFragmentManager
        .beginTransaction()
        .add(containerViewId, fragment, tag)
        .commitAllowingStateLoss()
}

fun FragmentActivity.showFragment(fragment: Fragment) {
    if (this.isFinishing) return
    supportFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss()
}

fun FragmentActivity.hideFragment(fragment: Fragment) {
    if (this.isFinishing) return
    supportFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss()
}

fun FragmentActivity.removeFragment(fragment: Fragment?) {
    if (this.isFinishing || fragment == null) return
    supportFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
}

fun FragmentActivity.findFragmentByTag(tag: String): Fragment? {
    if (this.isFinishing) return null
    return supportFragmentManager.findFragmentByTag(tag)
}


fun FragmentActivity.showDialogFragment(dialogFragment: DialogFragment,tag:String) {
    if (this.isFinishing) return
    val preDialogFragment = supportFragmentManager.findFragmentByTag(tag)
    (preDialogFragment as? DialogFragment)?.dismissAllowingStateLoss()
    dialogFragment.show(supportFragmentManager,tag)
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}