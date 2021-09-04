package com.fantasyapps.darmahealthcare.dialogs

import android.app.Activity
import android.app.ProgressDialog

class ProgressDialog {
    var mDialog: ProgressDialog? = null


    fun showProgressDialog(activity: Activity,  title: String, cancelable: Boolean) {
        mDialog = ProgressDialog(activity)

        if (mDialog == null)
            return

        mDialog!!.setCancelable(cancelable)

        if (mDialog!!.isShowing()) {
            mDialog!!.setTitle(title)
            return
        }
        if (mDialog != null && !mDialog!!.isShowing()) {
            mDialog!!.setTitle(title)
            mDialog!!.show()
        }
    }

    fun stopProgressDialog() {
        if (mDialog != null && mDialog!!.isShowing()) {
            mDialog!!.dismiss()
        }
    }
}