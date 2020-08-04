package anetos.software.byjuszyoin.data.model

import androidx.room.*
import anetos.software.byjuszyoin.data.db.typeconverter.ArticlesTypeConverter

/**
 * * [ArticlesDao]
 *
 * Interface for [ArticlesDao] type to interreact with with [AppDatabase] [RoomDatabse]
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */

@Dao
interface ArticlesDao {

    @Query("Select * from Articles")
    fun getAll(): List<Articles>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Articles>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: Articles)

    @Query("DELETE FROM Articles")
    fun deleteAll()
}