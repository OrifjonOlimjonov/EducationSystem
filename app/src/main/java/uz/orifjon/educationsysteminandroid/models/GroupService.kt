package uz.orifjon.educationsysteminandroid.models

import androidx.room.*

@Dao
interface GroupService {

    @Insert
    fun addGroup(group:Group)

    @Update
    fun editGroup(group: Group)

    @Delete
    fun deleteGroup(group: Group)

    @Query("SELECT * FROM group_1")
    fun listGroup():List<Group>


}