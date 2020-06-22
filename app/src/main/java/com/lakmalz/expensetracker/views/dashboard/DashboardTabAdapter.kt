package com.lakmalz.expensetracker.views.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lakmalz.expensetracker.base.BaseActivity
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.views.dashboard.account.AccountFragment

class DashboardTabAdapter(activity: BaseActivity, var list:MutableList<AccountsData>) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return AccountFragment.newInstance(list[position])
    }
}
