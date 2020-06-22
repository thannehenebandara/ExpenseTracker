package com.lakmalz.expensetracker.views.addnewtransaction.selectaccounttype

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.base.BaseActivity
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.utils.Constant
import com.lakmalz.expensetracker.utils.Utils.Companion.showMessageWithTwoButtons
import kotlinx.android.synthetic.main.activity_select_common_list.*
import kotlinx.android.synthetic.main.fragment_account.rv_list


class AccountSelectionListActivity : BaseActivity(), View.OnClickListener {

    private lateinit var viewModel: AccountSelectionListViewModel
    private lateinit var accountListAdapter: AccountListAdapter

    companion object {
        fun getIntent(context: Context) = Intent(context, AccountSelectionListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_common_list)
        viewModel = ViewModelProvider(this).get(AccountSelectionListViewModel::class.java)
        initUI()
        getData()
    }

    private fun initAdapter() {
        accountListAdapter =
            AccountListAdapter(::onClickItem, :: onLongPressedItem)
        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountListAdapter
        }
    }

    private fun getData() {
        viewModel.getAll().observe(this, Observer {
            accountListAdapter.setDataSet(it as ArrayList<AccountsData>)
        })
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.select_account)
        edt_name.hint = "Account name"
        initAdapter()
        txt_title_category.visibility = View.GONE
        btn_save.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun onClickItem(item: AccountsData) {
        setResult(item)
        onBackPressed()
    }

    private fun setResult(item: AccountsData) {
        val intent = Intent()
        intent.putExtra(Constant.EXTRAS_ACCOUNT_ITEM, item)
        setResult(Activity.RESULT_OK, intent)
    }

    override fun onClick(v: View?) {
        if (edt_name.text.isNullOrEmpty()) {
            showMessage("Account name is required.")
            return
        }
        val entity = AccountsData(edt_name.text.toString(), false)
        viewModel.insert(entity)
        edt_name.text.clear()
    }

    private fun onLongPressedItem(item: AccountsData) {

        showMessageWithTwoButtons(this,
            R.string.you_want_to_update_or_delete,
            R.string.btn_delete,
            R.string.btn_update,
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                if (item.isActive!!) {
                    showMessage("No access to delete ${item.name}")
                } else {
                    deleteDialog(item)
                }
            },
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                updateDialog(item)
            })

    }

    private fun deleteDialog(item: AccountsData) {
        showMessageWithTwoButtons(this,
            R.string.are_you_sure_you_want_to_delete_account,
            R.string.yes,
            R.string.no,
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                viewModel.delete(item)
            },
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
    }

    private fun updateDialog(item: AccountsData) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.update_account_name))
        val customLayout: View = layoutInflater.inflate(R.layout.custom_dailog_layout, null)
        val editText = customLayout.findViewById<EditText>(R.id.edt_name)
        editText.setText(item.name)
        builder.setView(customLayout)
        builder.setPositiveButton(getString(R.string.update),
            DialogInterface.OnClickListener { dialog, _ ->
                if (editText.text.isNullOrEmpty()) {
                    Toast.makeText(applicationContext, getString(R.string.account_name_is_required), Toast.LENGTH_LONG).show()
                }else{
                    item.name = editText.text.toString()
                    viewModel.update(item)
                    dialog.dismiss()
                }
            })
        builder.setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
