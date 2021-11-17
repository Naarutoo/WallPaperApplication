package subham.angel.moneymanager.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Model::class], version = 1)
abstract class MoneyManagerDatabase : RoomDatabase() {

    abstract fun dao(): MoneyManagerDao

    companion object {

        private var INSTANCE: MoneyManagerDatabase? = null

        fun getDatabaseObject(context: Context): MoneyManagerDatabase {

            if (INSTANCE == null) {

                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    MoneyManagerDatabase::class.java,
                    "task_db"
                )

                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
                return INSTANCE!!

            } else {
                return INSTANCE!!
            }
        }
    }
}