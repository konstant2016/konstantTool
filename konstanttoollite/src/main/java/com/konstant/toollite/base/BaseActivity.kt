package com.konstant.toollite.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.konstant.toollite.R
import com.konstant.toollite.eventbusparam.SwipeBackState
import com.konstant.toollite.eventbusparam.ThemeChanged
import com.konstant.toollite.util.Constant
import com.konstant.toollite.util.FileUtils
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 描述:所有activity的基类
 * 创建人:菜籽
 * 创建时间:2018/4/4 上午11:08
 * 备注:
 */

@SuppressLint("MissingSuperCall")
open class BaseActivity : SwipeBackActivity() {

    private val mSwipeBackLayout: SwipeBackLayout by lazy { swipeBackLayout }
    private var mRequestCode = 12
    private var mReason: String = ""
    private var mPermission: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(readTheme())
        EventBus.getDefault().register(this)
        // 沉浸状态栏
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }

        // 滑动返回
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)

        readSwipeBackState()
    }

    // 读取保存的主题状态
    private fun readTheme(): Int {
        val theme = FileUtils.readDataWithSharedPreference(this, Constant.NAME_SELECTED_THEME)
        theme ?: Constant.THEME_BLUE
        var themeId = 0
        when (theme) {
            Constant.THEME_RED -> {
                themeId = R.style.tool_lite_red
            }
            Constant.THEME_CLASS -> {
                themeId = R.style.tool_lite_class
            }
            Constant.THEME_BLUE -> {
                themeId = R.style.tool_lite_blue
            }
        }
        return themeId
    }

    // 读取保存的swipeback状态
    private fun readSwipeBackState() {
        val state = FileUtils.readDataWithSharedPreference(this, Constant.NAME_SWIPEBACK_STATE, false)
        mSwipeBackLayout.setEnableGesture(state)
    }

    // 更换主题
    @Subscribe
    fun changeTheme(msg: ThemeChanged) {
        recreate()
    }

    // 是否启用滑动返回
    @Subscribe
    open fun SwipeBackChanged(msg: SwipeBackState) {
        setSwipeBackEnable(msg.isState)
    }

    open protected fun initBaseViews() {
        findViewById(R.id.img_back).setOnClickListener { finish() }
    }

    // 设置主标题
    protected fun setTitle(s: String) {
        val view = findViewById(R.id.title_bar)
        val textView = view.findViewById(R.id.title) as TextView
        textView.text = s
    }

    override fun setTitle(stringId: Int) {
        val view = findViewById(R.id.title_bar)
        val textView = view.findViewById(R.id.title) as TextView
        textView.text = getText(stringId)
    }

    protected fun setSubTitle(s: String) {
        val view = findViewById(R.id.title_bar)
        val textView = view.findViewById(R.id.sub_title) as TextView
        textView.visibility = View.VISIBLE
        textView.text = s
    }

    protected fun setSubTitle(stringId: Int) {
        val view = findViewById(R.id.title_bar)
        val textView = view.findViewById(R.id.sub_title) as TextView
        textView.visibility = View.VISIBLE
        textView.text = getText(stringId)
    }


    // 隐藏软键盘
    protected fun hideSoftKeyboard() {
        if (window.attributes.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            return
        }
        if (currentFocus == null) {
            return
        }
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(currentFocus!!.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
    }

    // 滚动界面，防止输入法遮挡视图
    fun addLayoutListener(main: View, scroll: View) {
        main.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            //1、获取main在窗体的可视区域
            main.getWindowVisibleDisplayFrame(rect)
            //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
            val mainInvisibleHeight = main.rootView.height - rect.bottom
            val screenHeight = main.rootView.height//屏幕高度
            //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
            if (mainInvisibleHeight > screenHeight / 4) {
                val location = IntArray(2)
                scroll.getLocationInWindow(location)
                // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                val srollHeight = location[1] + scroll.height - rect.bottom
                //5､让界面整体上移键盘的高度
                main.scrollTo(0, srollHeight)
            } else {
                //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                main.scrollTo(0, 0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}