package com.lakmalz.expensetracker.views.addnewtransaction.selectaccounttype

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.utils.inflate
import kotlinx.android.synthetic.main.list_item_category.view.*

class AccountListAdapter(val onClickItem:(item: AccountsData) -> Unit,val onLongPressedItem:(item: AccountsData) -> Unit) : RecyclerView.Adapter<AccountListAdapter.ItemViewHolder>() {
    private var list: ArrayList<AccountsData> = ArrayList()
    fun setDataSet(_list: ArrayList<AccountsData>) {
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

    inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_category)) {
        fun bind(item: AccountsData) = with(itemView) {
            txt_transaction_name.text = item.name
            itemView.setOnClickListener {
                onClickItem(item)
            }
            itemView.setOnLongClickListener {
                onLongPressedItem(item)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(list[position])
}