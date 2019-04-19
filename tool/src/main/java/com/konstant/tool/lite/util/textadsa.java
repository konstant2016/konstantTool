package com.konstant.tool.lite.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

public class textadsa {

    void get(PackageInfo packageInfo){
        boolean a = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;
    }
}
