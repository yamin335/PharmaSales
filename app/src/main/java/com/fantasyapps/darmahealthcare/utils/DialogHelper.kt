package com.fantasyapps.darmahealthcare.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.progressDialog

object DialogHelper{

    fun with(context: Context): Build {
        return Build(context)
    }

    class Build() {
        internal var pDialog: ProgressDialog? = null
        internal var mContex: Context? = null

        constructor(context: Context) : this() {
            pDialog = ProgressDialog(context)
        }

        fun showProgress(title: String, message:String, iscancelable: Boolean): Build {
            pDialog?.setTitle(title)
            pDialog?.setMessage(message)
            pDialog?.setCancelable(iscancelable)
            pDialog?.show()
            return this
        }

        fun hideProgress(): Build{
            if (pDialog != null){
                pDialog?.dismiss()
            }
            return this
        }

    }
}
