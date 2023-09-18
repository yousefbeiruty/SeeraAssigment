package com.yousef.seera.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View

fun View.showAlertDialog(title: String, message: String, context: Context) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(true)
    builder.setPositiveButton(
        "ok",
        DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

    val alert: AlertDialog = builder.create()
    alert.show()
}