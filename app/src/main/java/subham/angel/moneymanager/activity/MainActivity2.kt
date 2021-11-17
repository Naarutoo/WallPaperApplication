package subham.angel.moneymanager.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import subham.angel.moneymanager.models.Model
import subham.angel.moneymanager.models.MoneyManagerDao
import subham.angel.moneymanager.models.MoneyManagerDatabase
import subham.angel.moneymanager.repository.MoneyManagerRepo
import subham.angel.moneymanager.viewmodels.MoneyManagerViewModel
import subham.angel.moneymanager.viewmodels.MoneyManagerViewModelFactory
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import subham.angel.moneymanager.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var db: MoneyManagerDatabase
    private lateinit var dao: MoneyManagerDao
    private lateinit var viewModel: MoneyManagerViewModel

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        db = MoneyManagerDatabase.getDatabaseObject(this)
        dao = db.dao()

        val repo = MoneyManagerRepo(dao)
        val viewModelFactory = MoneyManagerViewModelFactory(repo)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MoneyManagerViewModel::class.java)

        back.setOnClickListener { finish() }

        val data = intent.getSerializableExtra("data") as? Model

        if (data != null) {
            amount.text = data.amount.toString().toEditable()
            description.text = data.description.toEditable()
            submit.text = "Update"

            if (data.type == "Expense")
                spinner.setSelection(1)
        }

        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val currentDate = sdf.format(Date()).toString()

        date.text = currentDate

        submit.setOnClickListener {

            val amount = amount.text.toString()
            val description = description.text.toString()
            val type = spinner.selectedItem.toString()

            if (amount.isNotEmpty() && description.isNotEmpty()) {

                if (data == null) {

                    saveData(currentDate, type, amount, description)

                } else {

                    data.date = currentDate
                    data.amount = amount.toInt()
                    data.description = description

                    updateData(data)
                }

            } else {

                Toast.makeText(this, "At least one field is empty", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateData(model: Model) {

        CoroutineScope(Dispatchers.IO).launch {

            viewModel.update(model)

            finish()
        }
    }

    private fun saveData(date: String, type: String, amount: String, description: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val model = Model(type, amount.toInt(), description, date)

            viewModel.add(model)

            finish()
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}