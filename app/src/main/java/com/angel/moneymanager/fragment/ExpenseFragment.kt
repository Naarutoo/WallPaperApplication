package com.angel.moneymanager.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.angel.moneymanager.*
import com.angel.moneymanager.activity.MainActivity2
import com.angel.moneymanager.adapter.Adapter
import com.angel.moneymanager.models.MoneyManagerDao
import com.angel.moneymanager.models.MoneyManagerDatabase
import com.angel.moneymanager.interfaces.OnItemClickListener
import com.angel.moneymanager.models.Model
import com.angel.moneymanager.repository.MoneyManagerRepo
import com.angel.moneymanager.viewmodels.MoneyManagerViewModel
import com.angel.moneymanager.viewmodels.MoneyManagerViewModelFactory
import kotlinx.android.synthetic.main.fragment_expense.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseFragment : Fragment(R.layout.fragment_expense), OnItemClickListener {

    private val expenseList = mutableListOf<Model>()
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

        val adapter = Adapter(expenseList, context as Activity, this)
        recyclerView.adapter = adapter

        viewModel.get().observe(viewLifecycleOwner, Observer {

            expenseList.clear()

            for (i in it) {
                if (i.type == "Expense") {
                    expenseList.add(i)
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