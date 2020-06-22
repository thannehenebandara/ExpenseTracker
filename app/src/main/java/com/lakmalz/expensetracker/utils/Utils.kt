package com.lakmalz.expensetracker.utils

import android.content.Context
import android.content.DialogInterface
import android.text.format.DateFormat
import android.view.Window
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import java.text.NumberFormat
import java.util.*

class Utils {
    companion object {
        val customWeight = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.0f
        )

        fun getCurrencyInstance(): NumberFormat = NumberFormat.getCurrencyInstance()

        fun getFormattedCurrencyValue(value: Double): String {
            val format = getCurrencyInstance()
            format.currency = Currency.getInstance(getDefaultCurrencyCode())
            return format.format(value)
        }

        private fun getDefaultCurrencyCode(): String =
            Currency.getInstance(Locale.getDefault()).currencyCode

        fun getFormattedDate(context: Context?, updatedTimeInMilis: Long): String? {
            val smsTime = Calendar.getInstance()
            smsTime.timeInMillis = updatedTimeInMilis
            val now = Calendar.getInstance()
            val timeFormatString = "h:mm aa"
            val dateTimeFormatString = "EEEE, MMMM d, h:mm aa"
            val HOURS = 60 * 60 * 60.toLong()
            return when {
                now[Calendar.DATE] === smsTime[Calendar.DATE] -> {
                    "Today " + DateFormat.format(timeFormatString, smsTime)
                }
                now[Calendar.DATE] - smsTime[Calendar.DATE] === 1 -> {
                    "Yesterday " + DateFormat.format(timeFormatString, smsTime)
                }
                now[Calendar.YEAR] === smsTime[Calendar.YEAR] -> {
                    DateFormat.format(dateTimeFormatString, smsTime).toString()
                }
                else -> {
                    DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString()
                }
            }
        }

        fun showMessage(
            context: Context, @StringRes msg: Int,
            onClickListener: DialogInterface.OnClickListener?
        ) { showMessage(
                context,
                context.getString(msg),
                onClickListener
            )
        }


        fun showMessage(
            context: Context?,
            msg: String?,
            onClickListener: DialogInterface.OnClickListener?
        ) {
            val dialog =
                AlertDialog.Builder(context!!)
            dialog.setMessage(msg)
                .setPositiveButton("OK", onClickListener)
            val alert = dialog.create()
            alert.setCancelable(false)
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.show()
        }

        open fun showMessageWithTwoButtons(
            context: Context?,
            msg: Int,
            positiveText: Int,
            negativeText: Int,
            onYesClickListener: DialogInterface.OnClickListener?,
            onNoClickListener: DialogInterface.OnClickListener?
        ) {
            val dialog =
                androidx.appcompat.app.AlertDialog.Builder(context!!)
            dialog.setMessage(msg)
                .setPositiveButton(positiveText, onYesClickListener)
                .setNegativeButton(negativeText, onNoClickListener)
            val alert = dialog.create()
            alert.setCancelable(true)
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.show()
        }
    }


}