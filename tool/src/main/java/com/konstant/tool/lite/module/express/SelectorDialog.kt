package com.konstant.tool.lite.module.express

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.express.adapter.AdapterExpressCompany
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.layout_recycler_express_company.view.*

class SelectorDialog(context: Context) : KonstantDialog(context) {

    private var mListener: ((id: String, name: String) -> Unit)? = null

    init {
        hideNavigation()
        val view = LayoutInflater.from(context).inflate(R.layout.layout_recycler_express_company, null)
        addView(view)
        val adapterCompany = AdapterExpressCompany(context.resources.getStringArray(R.array.express_company), context.resources.getStringArray(R.array.express_company_id))
        with(view.layout_recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterCompany
        }
        adapterCompany.setOnItemClickListener { companyId, companyName ->
            mListener?.invoke(companyId, companyName)
            dismiss()
        }
    }

    fun setOnItemClickListener(listener: (id: String, name: String) -> Unit): SelectorDialog {
        mListener = listener
        return this
    }

}