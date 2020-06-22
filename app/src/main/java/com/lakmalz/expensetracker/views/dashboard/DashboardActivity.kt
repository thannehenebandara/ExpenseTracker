package com.lakmalz.expensetracker.views.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.base.BaseActivity
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.views.addnewtransaction.AddNewTransactionActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {

    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        initUI()
    }

    private fun initUI() {
        initActionBar()
        setTabBar()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_dashboard)
        setTabBar()
    }

    private fun setTabBar() {

        viewModel.getAllASC().observe(this, Observer {
            val tabAdapter = DashboardTabAdapter(this, it as ArrayList<AccountsData>)
            view_pager.adapter = tabAdapter
            val titleList = it.map { account ->
                account.name
            }
            TabLayoutMediator(tab_layout, view_pager) { tab: TabLayout.Tab, position: Int ->
                try {
                    tab.text = titleList[position]
                } catch (e: Exception) {
                }
            }.attach()
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                startActivity(AddNewTransactionActivity.getIntent(this))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

}
