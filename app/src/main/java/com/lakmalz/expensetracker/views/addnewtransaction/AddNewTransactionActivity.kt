package com.lakmalz.expensetracker.views.addnewtransaction

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.base.BaseActivity
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData
import com.lakmalz.expensetracker.data.db.entity.TransactionsData
import com.lakmalz.expensetracker.model.TransactionTypes
import com.lakmalz.expensetracker.utils.Constant.Companion.EXTRAS_ACCOUNT_ITEM
import com.lakmalz.expensetracker.utils.Constant.Companion.REQUEST_ACCOUNT_LIST
import com.lakmalz.expensetracker.utils.Constant.Companion.REQUEST_EXPENSE_LIST
import com.lakmalz.expensetracker.utils.Constant.Companion.REQUEST_INCOME_LIST
import com.lakmalz.expensetracker.utils.Utils
import com.lakmalz.expensetracker.views.addnewtransaction.expensecategory.ExpenseSelectionListActivity
import com.lakmalz.expensetracker.views.addnewtransaction.incomecategory.IncomeSelectionListActivity
import com.lakmalz.expensetracker.views.addnewtransaction.selectaccounttype.AccountSelectionListActivity
import kotlinx.android.synthetic.main.activity_add_new_transaction.*
import java.util.*


class AddNewTransactionActivity : BaseActivity(), View.OnClickListener {

    private lateinit var viewModel: AddNewTransactionViewModel
    var transactionType = TransactionTypes.EXPENSE.name

    companion object {
        fun getIntent(context: Context) = Intent(context, AddNewTransactionActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_transaction)
        viewModel = ViewModelProvider(this).get(AddNewTransactionViewModel::class.java)
        initUI()
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_add_transaction)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
        setTransactionType()
        edt_account.setOnClickListener(this)
        edt_category.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> {
                saveTransaction()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveTransaction() {
        if (!validation()) {
            return
        }

        val transaction = TransactionsData()
        val accountData = (edt_account.tag as AccountsData)
        transaction.accId = accountData.id
        when (transactionType) {
            TransactionTypes.INCOME.name -> {
                val income = edt_category.tag as IncomeCatData
                transaction.catId = income.id
                transaction.isIncome = true
                transaction.amount = edt_amount.text.toString().toDouble()
                transaction.catName = income.name

                income.isActive = true
                viewModel.updateIncomeCatType(income)
            }
            TransactionTypes.EXPENSE.name -> {
                val expense = edt_category.tag as ExpenseCatData
                transaction.catId = expense.id
                transaction.isIncome = false
                transaction.amount = -edt_amount.text.toString().toDouble()
                transaction.catName = expense.name

                expense.isActive = true
                viewModel.updateExpenseCatType(expense)
            }
        }
        transaction.currency = Currency.getInstance(Locale.getDefault()).currencyCode
        transaction.timestamp = System.currentTimeMillis()
        viewModel.insert(transaction)

        accountData.isActive = true
        viewModel.updateAccountType(accountData)
        Utils.showMessage(
            this,
            getString(R.string.transaction_added_successful),
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                onBackPressed()
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_transaction_menu, menu)
        return true
    }

    private fun setTransactionType() {
        val chipExpense = Chip(this)
        val chipIncome = Chip(this)
        Utils.customWeight.setMargins(getPixelsFromDp(this, 8f), 0, 0, 0)
        chipIncome.layoutParams = Utils.customWeight
        chipIncome.chipBackgroundColor = ContextCompat.getColorStateList(this, R.color.bg_chip)
        chipIncome.setTextColor(ContextCompat.getColorStateList(this, R.color.text_chip))
        chipIncome.text = getString(R.string.income)
        chipIncome.isFocusable = true
        chipIncome.isClickable = true
        chipIncome.textSize = 14.0f
        chipIncome.isCheckable = true
        chipIncome.checkedIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_check_circle_black_24dp)
        chipIncome.setOnClickListener {
            chipExpense.isChecked = false
            transactionType = TransactionTypes.INCOME.name
            resetFields()
            edt_category.hint = getString(R.string.select_income)
        }

        chipExpense.layoutParams = Utils.customWeight
        chipExpense.chipBackgroundColor = ContextCompat.getColorStateList(this, R.color.bg_chip)
        chipExpense.setTextColor(ContextCompat.getColorStateList(this, R.color.text_chip))
        chipExpense.text = getString(R.string.expense)
        chipExpense.isFocusable = true
        chipExpense.isClickable = true
        chipExpense.textSize = 14.0f
        chipExpense.isCheckable = true
        chipExpense.isChecked = true
        chipExpense.checkedIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_check_circle_black_24dp)
        chipExpense.setOnClickListener {
            chipIncome.isChecked = false
            transactionType = TransactionTypes.EXPENSE.name
            resetFields()
            edt_category.hint = getString(R.string.select_expense)
        }
        resetFields()
        chip_group_transaction_types.addView(chipExpense)
        chip_group_transaction_types.addView(chipIncome)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_ACCOUNT_LIST -> {
                    val item = data?.getParcelableExtra<AccountsData>(EXTRAS_ACCOUNT_ITEM)
                    edt_account.setText(item?.name)
                    edt_account.tag = item
                }
                REQUEST_INCOME_LIST -> {
                    val item = data?.getParcelableExtra<IncomeCatData>(EXTRAS_ACCOUNT_ITEM)
                    edt_category.setText(item?.name)
                    edt_category.tag = item
                }
                REQUEST_EXPENSE_LIST -> {
                    val item = data?.getParcelableExtra<ExpenseCatData>(EXTRAS_ACCOUNT_ITEM)
                    edt_category.setText(item?.name)
                    edt_category.tag = item
                }
            }
        }
    }

    private fun resetFields() {
        edt_category.hint = getString(R.string.select_expense)
        edt_account.hint = getString(R.string.select_account)
        edt_amount.hint =
            "${getString(R.string.enter_amount)} (${Utils.getCurrencyInstance().currency})"
        edt_amount.text.clear()
        edt_category.text.clear()
        edt_account.text.clear()
        edt_category.tag = null
    }

    private fun validation(): Boolean {
        when {
            edt_account.tag == null -> {
                showMessage(getString(R.string.account_name_is_required))
                return false
            }
            edt_category.tag == null -> {
                if (transactionType == TransactionTypes.INCOME.name) {
                    showMessage(getString(R.string.income_is_required))
                } else {
                    showMessage(getString(R.string.expense_is_required))
                }
                return false
            }
            edt_amount.text.isNullOrEmpty() -> {
                showMessage(getString((R.string.amount_is_required)))
                return false
            }
            else -> return true
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            edt_category.id -> {
                clickOnCategory()
            }
            edt_account.id -> {
                clickOnAccount()
            }
        }
    }

    private fun clickOnAccount() {
        val intent = AccountSelectionListActivity.getIntent(this)
        startActivityForResult(intent, REQUEST_ACCOUNT_LIST)
    }

    private fun clickOnCategory() {
        when (transactionType) {
            TransactionTypes.INCOME.name -> {
                val intent = IncomeSelectionListActivity.getIntent(this)
                startActivityForResult(intent, REQUEST_INCOME_LIST)
            }
            TransactionTypes.EXPENSE.name -> {
                val intent = ExpenseSelectionListActivity.getIntent(this)
                startActivityForResult(intent, REQUEST_EXPENSE_LIST)
            }
        }
    }
}
