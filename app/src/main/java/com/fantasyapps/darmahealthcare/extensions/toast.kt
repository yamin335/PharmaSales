package com.fantasyapps.darmahealthcare.extensions

import android.content.Context
import android.media.AudioRecord
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import com.fantasyapps.darmahealthcare.BuildConfig


/*
*
*
* usage
*
* showShortToast("Text")
*
*
*/

fun Context.showShortToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.showDebugShortToast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    if (BuildConfig.DEBUG){
        if (text != null){
            Toast.makeText(this, text, duration).show()
        }
    }
}

fun Context.showLongToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, duration).show()
}

fun Context.showShortToastTop(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)

    toast.setGravity(Gravity.TOP or Gravity.END, 100, 200)
    toast.show()
}

fun Context.showLongToastTop(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

    toast.setGravity(Gravity.TOP or Gravity.END, 100, 200)
    toast.show()
}