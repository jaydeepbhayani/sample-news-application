package anetos.software.byjuszyoin.data.model

import androidx.room.*

@Dao
interface RouteDao {

    @Query("Select * from SafeRoute")
    fun getAll(): List<SafeRoute>

    @Query("Select * from SafeRoute where start =:startingPoint and destination = :destinationPoint")
    fun getByLocations(startingPoint:String, destinationPoint:String): SafeRoute

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(location: List<SafeRoute>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: SafeRoute)

    @Delete
    fun delete(location: SafeRoute)
}