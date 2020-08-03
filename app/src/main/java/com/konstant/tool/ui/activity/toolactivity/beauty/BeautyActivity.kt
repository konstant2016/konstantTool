package com.konstant.tool.ui.activity.toolactivity.beauty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.konstant.tool.R
import com.konstant.tool.base.BaseActivity
import com.konstant.tool.util.NetworkUtil
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_beauty.*
import org.json.JSONObject


/**
 * 描述:美女图片
 * 创建人:菜籽
 * 创建时间:2018/1/12 下午1:46
 * 备注:
 */

class BeautyActivity : BaseActivity() {

    private val mUrlList = ArrayList<String>()
    private val mBaseUrl = "https://hot.browser.miui.com/opg-api/item?gid="
    private var mPageIndex = (Math.random() * 1000 + 2807480).toInt()
    private var isPullDown = true
    private val mAdapter by lazy { BeautyActivity.MyAdapter(mUrlList, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beauty)
        setTitle("美女图片")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        // 刷新监听
        refresh_layout.setHeaderView(BezierLayout(this))
        refresh_layout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                isPullDown = true
                mPageIndex -= 2
                getData()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                isPullDown = false
                mPageIndex += 2
                getData()
            }
        })

        // listview的点击监听
        layout_listview.adapter = mAdapter
        layout_listview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@BeautyActivity, LookPicActivity::class.java)
            intent.putExtra("urlString", mUrlList!![position])
            intent.putStringArrayListExtra("urlList", mUrlList)
            intent.putExtra("index", position)
            startActivity(intent)
        }

        // 进来时就刷新
        refresh_layout.startRefresh()
    }


    // 获取网络数据
    private fun getData() {
        NetworkUtil.get(mBaseUrl + mPageIndex, "") { state, data ->
            Log.i("MIUI图片", data)
            if (data.isNullOrEmpty() or (data.length < 150)) {
                mPageIndex += (Math.random() * 8 - 4).toInt()
                getData()
                return@get
            }
            refreshUI(parseUrlList(data))
        }
    }

    // 手动解析，整合数据
    private fun parseUrlList(string: String): ArrayList<String> {
        val urlList = ArrayList<String>()
        val obj = JSONObject(string)
        val array = obj.optJSONArray("data")

        (0 until array.length()).map {
            urlList.add(array.optJSONObject(it).optString("url"))
        }

        return urlList
    }

    // 刷新界面
    private fun refreshUI(urlList: ArrayList<String>) {
        Log.i("beauey","开始刷新界面")
        runOnUiThread {
            if (isPullDown) {
                mUrlList.clear()
                refresh_layout.finishRefreshing()
            } else {
                refresh_layout.finishLoadmore()
            }
            mUrlList.addAll(urlList)
            mAdapter.notifyDataSetChanged()
        }
    }


    internal class MyAdapter(private val imageURLList: List<String>, private val mContext: Context) : BaseAdapter() {

        override fun getCount(): Int {
            return imageURLList.size
        }

        override fun getItem(i: Int): Any {
            return imageURLList[i]
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var view = view
            val inflater = LayoutInflater.from(mContext)
            val imageView: ImageView
            if (view == null) {
                view = inflater.inflate(R.layout.item_listview_beauty, null)
                imageView = view!!.findViewById(R.id.photoView) as ImageView
                view!!.tag = imageView
            } else {
                imageView = view!!.getTag() as ImageView
            }
            Log.i("test", "imageurl：" + imageURLList[i])
            Picasso.with(mContext).load(imageURLList[i]).into(imageView)
            return view
        }
    }

}