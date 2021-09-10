package com.konstant.tool.ui.activity.cardSwipe.demo04;

import android.content.Context;

public class DisplayUtil {

    /**
     * fun Context.dp2px(dpValue: Float): Int {
     *     val scale = resources.displayMetrics.density
     *     return (dpValue * scale + 0.5f).toInt()
     * }
     * @return
     */

    static int dip2px(Context context,int dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
