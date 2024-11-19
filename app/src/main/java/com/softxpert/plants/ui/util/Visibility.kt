package com.softxpert.plants.ui.util

import android.view.View


fun View.toVisible(){
    this.visibility = View.VISIBLE
}

fun View.toGone(){
    this.visibility = View.GONE
}

fun View.toInVisible(){
    this.visibility = View.INVISIBLE
}