package anetos.software.byjuszyoin.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Application Database
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */

@Database(
    entities = [Articles::class, SafeRoute::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

    abstract fun routeDao(): RouteDao

    companion object {
        private const val DB_NAME = "BYJUSzyoin"
        private var appDb: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (null == appDb) {
                appDb = buildDatabaseInstance(context)
            }
            return appDb as AppDatabase
        }

        private fun buildDatabaseInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    /*companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app.db").build()
    }*/
}