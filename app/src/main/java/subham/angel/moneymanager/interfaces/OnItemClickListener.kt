package subham.angel.moneymanager.interfaces

import subham.angel.moneymanager.models.Model

interface OnItemClickListener {

    fun onDeleteClick(model: Model)
    fun onEditClick(model: Model)
}