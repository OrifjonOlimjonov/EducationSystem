package uz.orifjon.educationsysteminandroid.models

import androidx.room.*
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper

@Dao
interface MentorService {

    @Insert
    fun addMentor(mentor: Mentor)

    @Update
    fun editMentor(mentor: Mentor)

    @Delete
    fun deleteMentor(mentor: Mentor)

    @Query("SELECT * FROM mentor")
    fun listMentor():List<Mentor>

    @Query("SELECT * FROM mentor WHERE speciality = :tool")
    fun getByGroupMentor(tool:String):List<Mentor>
}