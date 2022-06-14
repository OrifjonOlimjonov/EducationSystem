package uz.orifjon.educationsysteminandroid.models

import androidx.room.*
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper

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

    @Query("SELECT * FROM group_1 WHERE id = :id")
    fun getGroupById(id:Long):Group


    @Query("SELECT * FROM group_1 WHERE mentor_id = :id ")
    fun getMentorGroupList(id: Long):List<Group>

    @Query("SELECT * FROM group_1 WHERE group_is_open = :selected AND course_id = :type")
    fun getGroupList(selected:Long,type:Long):List<Group>

}