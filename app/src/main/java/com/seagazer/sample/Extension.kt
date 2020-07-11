package com.seagazer.sample

import android.content.Context
import android.widget.Toast

/**
 *
 * Author: Seagazer
 * Date: 2020/4/23
 */
fun Context.toastShort(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
