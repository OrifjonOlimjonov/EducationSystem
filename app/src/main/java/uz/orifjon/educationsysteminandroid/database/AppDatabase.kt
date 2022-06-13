package uz.orifjon.educationsysteminandroid.database

import android.content.Context
import androidx.room.*
import uz.orifjon.educationsysteminandroid.models.*

@Database(entities = [Student::class, Group::class,Mentor::class,Course::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase(){

        abstract fun studentDao():StudentService
        abstract fun courseDao():CourseService
        abstract fun groupDao():GroupService
        abstract fun mentorDao():MentorService

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "education_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return  instance
            }
        }

    }
}





