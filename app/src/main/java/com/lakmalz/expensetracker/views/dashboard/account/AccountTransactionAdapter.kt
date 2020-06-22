package com.lakmalz.expensetracker.views.dashboard.account

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.data.db.entity.TransactionsData
import com.lakmalz.expensetracker.utils.Utils
import com.lakmalz.expensetracker.utils.inflate
import kotlinx.android.synthetic.main.list_item_transaction.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AccountTransactionAdapter(val onLongPressItem: (item: TransactionsData) -> Unit) :
    RecyclerView.Adapter<AccountTransactionAdapter.ItemViewHolder>() {
    var format: NumberFormat = Utils.getCurrencyInstance()
    private var list: ArrayList<TransactionsData> = ArrayList()
    fun setDataSet(_list: ArrayList<TransactionsData>) {
        list.clear()
        list = _list
        notifyDataSetChanged()
    }

    fun removeAt(position: Int?) {
        position?.let {
            list.removeAt(it)
            notifyItemRemoved(it)
        }
    }

    inner class ItemViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_transaction)) {
        fun bind(item: TransactionsData) = with(itemView) {
            txt_transaction_date.text = item.timestamp?.let { Utils.getFormattedDate(context, it) }
            format.currency = Currency.getInstance(item.currency)
            val amount = format.format(item.amount)
            txt_amount.text = "$amount"
            txt_amount.setTextColor(ContextCompat.getColor(context, R.color.red_text))
            if (item.isIncome) {
                txt_amount.setTextColor(ContextCompat.getColor(context, R.color.green_text))
            }
            txt_transaction_name.text = item.catName
            itemView.setOnLongClickListener {
                onLongPressItem(item)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(list[position])
}