package com.softxpert.plants.ui.util


fun String?.orNA(): String {
    return if (this.isNullOrEmpty()) "NA" else this
}