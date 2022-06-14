package uz.orifjon.educationsysteminandroid.models

import androidx.room.*
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper

@Dao
interface StudentService {

    @Insert
    fun addStudent(student: Student)

    @Update
    fun editStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Query("SELECT * FROM student")
    fun listStudent():List<Student>

    @Query("SELECT * FROM student WHERE group_id = :id")
    fun listGroupStudent(id:Long):List<Student>

    @Query("SELECT * FROM student WHERE group_id = :id")
    fun getGroupByStudent(id: Long):List<Student>
}