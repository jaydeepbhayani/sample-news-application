package anetos.software.byjuszyoin.data.model

import androidx.room.*

@Dao
interface ArticlesDao {

    @Query("Select * from Articles")
    fun getAll(): List<Articles>

    /*@Query("Select * from TopHeadLinesResponse where latitude = :lat and longitude =:lon")
    fun getByLatLon(lat: String, lon: String): TopHeadLinesResponse

    @Query("Select * from TopHeadLinesResponse where placeName = :placename")
    fun getByPlaceName(placename: String): TopHeadLinesResponse*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Articles>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: Articles)

    @Delete
    fun delete(articles: Articles)
}