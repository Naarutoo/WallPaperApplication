package subham.angel.moneymanager.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoneyManagerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(model: Model)

    @Query("select * from MMDB")
    fun get(): LiveData<List<Model>>

    @Update
    fun update(model: Model)

    @Delete
    fun delete(model: Model)
}