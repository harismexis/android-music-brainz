package com.example.musicbrainz.framework.extensions

import android.view.View
import android.widget.TextView
import com.example.musicbrainz.R

fun TextView?.setTextOrDefault(value: String?, defaultValue: String) {
    if (this == null) return
    if (value.isNullOrBlank()) {
        this.text = defaultValue
        return
    }
    this.text = value
}

fun TextView?.setTextOrUnknown(value: String?) {
    if (this == null) return
    this.setTextOrDefault(value, this.context.getString(R.string.missing_value))
}

fun TextView?.setTextAndVisibility(
    value: String?,
    txtvLabel: TextView? = null
) {
    if (this == null) return
    if (!value.isNullOrBlank()) {
        this.text = value
        this.visibility = View.VISIBLE
        txtvLabel?.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
        txtvLabel?.visibility = View.GONE
    }
}
