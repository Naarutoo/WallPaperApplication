package subham.angel.moneymanager.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import subham.angel.moneymanager.R
import subham.angel.moneymanager.models.MoneyManagerDao
import subham.angel.moneymanager.models.MoneyManagerDatabase
import subham.angel.moneymanager.repository.MoneyManagerRepo
import subham.angel.moneymanager.viewmodels.MoneyManagerViewModel
import subham.angel.moneymanager.viewmodels.MoneyManagerViewModelFactory
import kotlinx.android.synthetic.main.fragment_balance.*

class BalanceFragment : Fragment(R.layout.fragment_balance) {

    private lateinit var db: MoneyManagerDatabase
    private lateinit var dao: MoneyManagerDao
    private lateinit var viewModel: MoneyManagerViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        db = MoneyManagerDatabase.getDatabaseObject(context)
        dao = db.dao()

        val repo = MoneyManagerRepo(dao)
        val viewModelFactory = MoneyManagerViewModelFactory(repo)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MoneyManagerViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showData()
    }

    @SuppressLint("SetTextI18n")
    private fun showData() {

        var incomeCount = 0
        var expenseCount = 0

        viewModel.get().observe(viewLifecycleOwner, Observer {

            for (i in it) {

                if (i.type == "Income") {

                    incomeCount += i.amount

                } else {

                    expenseCount += i.amount
                }
            }

            income.text = "Income : $incomeCount"
            expense.text = "Expense : $expenseCount"
            total.text = "Total : ${incomeCount - expenseCount}"

        })
    }
}