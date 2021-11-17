package subham.angel.moneymanager.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import subham.angel.moneymanager.*
import subham.angel.moneymanager.activity.MainActivity2
import subham.angel.moneymanager.adapter.Adapter
import subham.angel.moneymanager.models.MoneyManagerDao
import subham.angel.moneymanager.models.MoneyManagerDatabase
import subham.angel.moneymanager.interfaces.OnItemClickListener
import subham.angel.moneymanager.models.Model
import subham.angel.moneymanager.repository.MoneyManagerRepo
import subham.angel.moneymanager.viewmodels.MoneyManagerViewModel
import subham.angel.moneymanager.viewmodels.MoneyManagerViewModelFactory
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncomeFragment : Fragment(R.layout.fragment_income), OnItemClickListener {

    private val incomeList = mutableListOf<Model>()
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
        fetchData()
    }

    private fun fetchData() {

        val adapter = Adapter(incomeList, context as Activity, this)
        recyclerView.adapter = adapter

        viewModel.get().observe(viewLifecycleOwner, Observer {

            incomeList.clear()

            for (i in it) {
                if (i.type == "Income") {
                    incomeList.add(i)
                }
            }

            adapter.notifyDataSetChanged()
        })
    }

    override fun onDeleteClick(model: Model) {

        viewModel.delete(model)
    }

    override fun onEditClick(model: Model) {

        val intent = Intent(context, MainActivity2::class.java)
        intent.putExtra("data", model)
        startActivity(intent)
    }
}