package subham.angel.moneymanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "MMDB")
data class Model(

    @ColumnInfo(name = "type") var type: String = "",
    @ColumnInfo(name = "amount") var amount: Int = 0,
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "date") var date: String = ""

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}