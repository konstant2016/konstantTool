package com.konstant.tool.lite.base

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.module.beauty.activity.BeautyActivity
import com.konstant.tool.lite.module.busline.activity.BusRouteActivity
import com.konstant.tool.lite.module.compass.CompassActivity
import com.konstant.tool.lite.module.deviceinfo.DeviceInfoActivity
import com.konstant.tool.lite.module.express.activity.ExpressListActivity
import com.konstant.tool.lite.module.qrcode.QRCodeActivity
import com.konstant.tool.lite.module.ruler.RulerActivity
import com.konstant.tool.lite.module.setting.activity.SettingActivity
import com.konstant.tool.lite.module.setting.param.SwipeBackState
import com.konstant.tool.lite.module.setting.param.ThemeChanged
import com.konstant.tool.lite.module.setting.param.UserHeaderChanged
import com.konstant.tool.lite.module.translate.TranslateActivity
import com.konstant.tool.lite.module.weather.activity.WeatherActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_drawer_left.*
import kotlinx.android.synthetic.main.title_layout.*
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
abstract class BaseActivity : SwipeBackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(SettingManager.getTheme(this))
        super.setContentView(R.layout.activity_base)

        EventBus.getDefault().register(this)
        // 沉浸状态栏
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 隐藏导航栏
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
                navigationBarColor = run {
                    val value = TypedValue()
                    theme.resolveAttribute(R.attr.colorPrimary, value, true)
                    value.data
                }
            }
        }

        // 滑动返回
        swipeBackLayout.apply {
            setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
            setEnableGesture(SettingManager.getSwipeBackState(this@BaseActivity))
        }

        initDrawLayout()

        onUserHeaderChanged(UserHeaderChanged())

    }

    override fun setContentView(layoutResID: Int) {
        layoutInflater.inflate(layoutResID, base_content, true)
    }

    // 更换主题
    @Subscribe
    fun changeTheme(msg: ThemeChanged) {
        recreate()
    }

    // 是否启用滑动返回
    @Subscribe
    open fun onSwipeBackChanged(msg: SwipeBackState) {
        setSwipeBackEnable(msg.state)
    }

    // 用户头像发生了变化
    @Subscribe
    open fun onUserHeaderChanged(msg: UserHeaderChanged) {
        val bitmap = SettingManager.getUserHeaderThumb(this)
        with(RoundedBitmapDrawableFactory.create(resources, bitmap)) {
            paint.isAntiAlias = true
            cornerRadius = Math.max(bitmap.width.toFloat(), bitmap.height.toFloat())
            drawer_header.setImageDrawable(this)
        }
    }

    protected open fun initBaseViews() {
        title_bar.setOnClickListener { hideSoftKeyboard() }
        findViewById(R.id.img_back).setOnClickListener { finish() }
    }

    // 设置主标题
    protected fun setTitle(s: String) {
        val view = findViewById(R.id.title_bar)
        val textView = view.findViewById(R.id.title) as TextView
        textView.text = s
    }

    // 隐藏主标题
    protected fun hideTitleBar() {
        title_bar.visibility = View.GONE
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
    protected fun addLayoutListener(main: View, scroll: View) {
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

    // 展示吐司
    fun showToast(msg: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    protected fun startActivity(cls: Class<*>) {
        if (draw_layout.isDrawerOpen(Gravity.LEFT)) {
            draw_layout.closeDrawers()
        }
        startActivity(Intent(this, cls))
    }

    // 初始化侧边栏
    private fun initDrawLayout() {

        text_translate.setOnClickListener { startActivity(TranslateActivity::class.java) }

        text_beauty.setOnClickListener { startActivity(BeautyActivity::class.java) }

        text_compass.setOnClickListener { startActivity(CompassActivity::class.java) }

        text_qrcode.setOnClickListener { startActivity(QRCodeActivity::class.java) }

        text_express.setOnClickListener { startActivity(ExpressListActivity::class.java) }

        text_bus_line.setOnClickListener { startActivity(BusRouteActivity::class.java) }

        text_device_info.setOnClickListener { startActivity(DeviceInfoActivity::class.java) }

        text_weather.setOnClickListener { startActivity(WeatherActivity::class.java) }

        text_ruler.setOnClickListener { startActivity(RulerActivity::class.java) }

        text_zfb.setOnClickListener { zfb() }

        text_mian.setOnClickListener { startActivity(MainActivity::class.java) }

        text_setting.setOnClickListener { startActivity(SettingActivity::class.java) }

    }

    // 跳转到支付宝红包页面
    fun zfb() {
        val clipBoard = this.getSystemService(SwipeBackActivity.CLIPBOARD_SERVICE) as ClipboardManager
        clipBoard.text = "LMGGea11Mh"
        try {
            val packageManager = this.applicationContext.packageManager
            val intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone")
            startActivity(intent)
        } catch (e: Exception) {
            val url = "https://ds.alipay.com/?from=mobileweb"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}