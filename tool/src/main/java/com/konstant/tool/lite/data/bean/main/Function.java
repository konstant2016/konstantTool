package com.konstant.tool.lite.data.bean.main;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.konstant.tool.lite.base.KonApplication;
import com.konstant.tool.lite.module.setting.SettingManager;

public class Function {

    /**
     * title : 翻译
     * title_en : TRANSLATE
     * type : 1
     */

    private String title;
    private String title_en;
    private String type;

    public String getTitle() {
        if (SettingManager.INSTANCE.getShowChinese(KonApplication.context)) {
            return title;
        } else {
            return title_en;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Function)) return false;
        Function other = (Function) obj;
        return TextUtils.equals(title, other.title);
    }
}
