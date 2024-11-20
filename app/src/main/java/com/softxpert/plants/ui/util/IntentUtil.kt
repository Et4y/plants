package com.softxpert.plants.ui.util

import android.app.Activity
import android.content.Intent

fun Activity.openLink(url: String) {
    val i = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
    this.startActivity(i)
}
