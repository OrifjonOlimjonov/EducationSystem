package uz.orifjon.educationsysteminandroid.models

import androidx.room.*

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
}