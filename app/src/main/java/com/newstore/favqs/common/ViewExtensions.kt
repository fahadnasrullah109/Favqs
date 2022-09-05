package com.newstore.favqs.common

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Fragment.showToast(msg : String) {
    this.context?.let {
        Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
    }
}