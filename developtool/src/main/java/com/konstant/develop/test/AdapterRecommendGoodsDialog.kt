
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.develop.R
import com.konstant.develop.test.RevenueScrollVideoGoods

/**
 * 时间：2021/12/6 6:10 下午
 * 作者：吕卡
 * 备注：推荐商品弹窗的适配器
 */

class AdapterRecommendGoodsDialog(private val goodsList: MutableList<RevenueScrollVideoGoods>) : RecyclerView.Adapter<AdapterRecommendGoodsDialog.RecommendViewHolder>() {

    inner class RecommendViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_recommend_dialog, parent, false)
        return RecommendViewHolder(view)
    }

    override fun getItemCount() = goodsList.size

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {

    }

}