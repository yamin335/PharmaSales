package com.fantasyapps.darmahealthcare.utils;

import android.app.Activity;
import android.os.Build;

public class ActionBar {
    /** Show/hide ActionBar for  KitKat devices */
    public static void ABkk(Activity A, int count) {
        if (lollipop()) return;     // No problem when using Toolbar
        android.app.ActionBar ab = A.getActionBar();
        if (ab==null) return;
        if (count==1) { ab.hide(); }
        if (count==0) { ab.show(); }
    }

    /** Return true if API 21 or greater */
    private static boolean lollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
