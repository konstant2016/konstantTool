package com.konstant.tool.ui.activity.toolactivity.im.activity

import android.os.Bundle
import com.konstant.tool.R
import com.konstant.tool.base.BaseActivity
import com.konstant.tool.ui.activity.toolactivity.im.fragment.ContactsFragment
import com.konstant.tool.ui.activity.toolactivity.im.fragment.ConversionFragment
import com.konstant.tool.ui.activity.toolactivity.im.fragment.FinderFragment
import kotlinx.android.synthetic.main.activity_im.*

class IMActivity : BaseActivity() {

    private val mConversionFragment by lazy { ConversionFragment() }
    private val mContactFragment by lazy { ContactsFragment() }
    private val mFinderFragment by lazy { FinderFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_im)
        setTitle("环信即时通讯")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        // 默认展示会话的fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.layout_content, mConversionFragment)
                .add(R.id.layout_content, mContactFragment)
                .add(R.id.layout_content, mFinderFragment)
                .show(mConversionFragment).hide(mContactFragment).hide(mFinderFragment)
                .commit()

        // RadioButton的点击事件
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            val transaction = supportFragmentManager.beginTransaction()
            when (checkedId) {
                R.id.radio_chat ->
                    transaction.hide(mContactFragment).hide(mFinderFragment).show(mConversionFragment)
                R.id.radio_contact ->
                    transaction.hide(mConversionFragment).hide(mFinderFragment).show(mContactFragment)
                R.id.radio_finder ->
                    transaction.hide(mConversionFragment).hide(mContactFragment).show(mFinderFragment)
            }
            transaction.commit()
        }
    }
}
