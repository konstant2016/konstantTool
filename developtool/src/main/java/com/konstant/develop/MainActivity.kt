package com.konstant.develop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.konstant.develop.bitmap.BitmapActivity
import com.konstant.develop.dsl.DSLActivity
import com.konstant.develop.jetpack.paging3.Paging3Activity
import com.konstant.develop.tree.TreeViewActivity
import com.konstant.develop.x5.SystemWebActivity
import com.konstant.develop.x5.TencentX5Activity
import com.konstant.develop.yangcong.YangCongDebugActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mToggle by lazy { ActionBarDrawerToggle(this, draw_layout, 0, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setFullScreenStatusBarDarkMode(this)
        NotchModeUtil.setNotchMode(this)
        setContentView(R.layout.activity_main)
        setTitleBar()
        initBaseViews()

        tv_multi.setText("前面已经讲过，一个文字在界面中，往往需要占用比他的实际显示宽度更多一点的宽度，以此来让文字和文字之间保留一些间距，不会显得过于拥挤",18f)
        tv_multi.isSelected = true
        tv_multi.setOnClickListener {
            tv_multi.isSelected = !tv_multi.isSelected
        }
    }

    private fun setTitleBar(){
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToggle.syncState()
        draw_layout.addDrawerListener(mToggle)
    }

    private fun initBaseViews() {
        btn_teacher.setOnClickListener {
            openApp("com.yangcong345.teacher")
        }
        setting_teacher.setOnClickListener {
            openSetting("com.yangcong345.teacher")
        }
        btn_student.setOnClickListener {
            openApp("com.yangcong345.onionschool")
        }
        setting_student.setOnClickListener {
            openSetting("com.yangcong345.onionschool")
        }
        btn_middle.setOnClickListener {
            openApp("com.yangcong345.android.middle2")
        }
        setting_middle.setOnClickListener {
            openSetting("com.yangcong345.android.middle2")
        }
        btn_public.setOnClickListener {
            openApp("com.yangcong345.android.phone")
        }
        setting_public.setOnClickListener {
            openSetting("com.yangcong345.android.phone")
        }
        btn_setting.setOnClickListener {
            openApp("com.android.settings")
        }
        btn_wifi.setOnClickListener {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(intent)
        }
        btn_scheme.setOnClickListener {
            if (TextUtils.isEmpty(et_scheme.text)) return@setOnClickListener
            try {
                val s = et_scheme.text.toString()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(s))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "没有应用可以响应SCHEME", Toast.LENGTH_LONG).show()
            }
        }
        layout_parent.setOnClickListener {
            hideSoftKeyboard()
        }
        btn_x5.setOnClickListener {
            val intent = Intent(this, TencentX5Activity::class.java)
            startActivity(intent)
        }
        btn_system.setOnClickListener {
            val intent = Intent(this, SystemWebActivity::class.java)
            startActivity(intent)
        }
        btn_bitmap.setOnClickListener {
            val intent = Intent(this, BitmapActivity::class.java)
            intent.putExtra("glide",false)
            startActivity(intent)
        }

        btn_dsl.setOnClickListener {
            val intent = Intent(this, DSLActivity::class.java)
            startActivity(intent)
        }
        btn_yc_debug.setOnClickListener {
            val intent = Intent(this, YangCongDebugActivity::class.java)
            startActivity(intent)
        }
        btn_tree_view.setOnClickListener {
            val intent = Intent(this, TreeViewActivity::class.java)
            startActivity(intent)
        }
        btn_paging3.setOnClickListener {
            val intent = Intent(this, Paging3Activity::class.java)
            startActivity(intent)
        }
    }

    private fun openApp(packageName: String) {
        try {
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "找不到应用", Toast.LENGTH_LONG).show()
        }
    }

    private fun openSetting(packageName: String) {
        try {
            val packageIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (packageIntent == null){
                Toast.makeText(this, "找不到应用", Toast.LENGTH_LONG).show()
                return
            }

            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "找不到应用", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.icon_qr_scan){
            startActivity(Intent(this, QRScanActivity::class.java))
        }
        if (mToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // 隐藏软键盘
    private fun hideSoftKeyboard() {
        if (window.attributes.softInputMode ==
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            return
        }
        if (currentFocus == null) {
            return
        }
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(currentFocus?.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
    }
}