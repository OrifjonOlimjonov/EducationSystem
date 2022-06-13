package uz.orifjon.educationsysteminandroid.models

import androidx.room.*

@Dao
interface CourseService {

    @Insert
    fun addCourse(course: Course)

    @Update
    fun editCourse(course: Course)

    @Delete
    fun deleteCourse(course: Course)

    @Query("SELECT * FROM course")
    fun listCourse():List<Course>
}