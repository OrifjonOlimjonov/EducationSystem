package uz.orifjon.educationsysteminandroid.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.orifjon.educationsysteminandroid.models.Course
import uz.orifjon.educationsysteminandroid.models.Group
import uz.orifjon.educationsysteminandroid.models.Mentor
import uz.orifjon.educationsysteminandroid.models.Student

class MySqliteHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
), DatabaseService {

    companion object {
        const val DATABASE_NAME = "education"
        const val DATABASE_VERSION = 1

        const val COURSE_TABLE = "course"
        const val COURSE_ID = "id"
        const val COURSE_NAME = "name"
        const val COURSE_DESCRIPTION = "description"

        const val GROUP_TABLE = "group1"
        const val GROUP_ID = "id"
        const val GROUP_NAME = "name"
        const val GROUP_IS_OPEN = "open"
        const val GROUP_COURSE_ID = "course_id"
        const val GROUP_MENTOR_ID = "mentor_id"
        const val GROUP_DATE = "date"
        const val GROUP_TYPE = "speciality"

        const val MENTOR_TABLE = "mentor"
        const val MENTOR_ID = "id"
        const val MENTOR_FIRSTNAME = "firstname"
        const val MENTOR_LASTNAME = "lastname"
        const val MENTOR_PATRON = "patron"
        const val MENTOR_SPECIALITY = "speciality"


        const val STUDENT_TABLE = "student"
        const val STUDENT_ID = "id"
        const val STUDENT_FIRSTNAME = "firstname"
        const val STUDENT_LASTNAME = "lastname"
        const val STUDENT_PATRON = "patron"
        const val STUDENT_REGISTER_DATE = "register_date"
        const val STUDENT_GROUP_ID = "group_id"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query1 =
            """CREATE TABLE $COURSE_TABLE($COURSE_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $COURSE_NAME TEXT NOT NULL, $COURSE_DESCRIPTION TEXT NOT NULL)"""
        val query2 =
            """CREATE TABLE $GROUP_TABLE($GROUP_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $GROUP_NAME TEXT NOT NULL,$GROUP_IS_OPEN INTEGER NOT NULL,$GROUP_COURSE_ID INTEGER NOT NULL,$GROUP_MENTOR_ID INTEGER NOT NULL,$GROUP_DATE TEXT NOT NULL,$GROUP_TYPE TEXT NOT NULL,FOREIGN KEY($GROUP_COURSE_ID) REFERENCES  $COURSE_TABLE($COURSE_ID),FOREIGN KEY($GROUP_MENTOR_ID) REFERENCES  $MENTOR_TABLE($MENTOR_ID))"""
        val query3 =
            """CREATE TABLE $MENTOR_TABLE($MENTOR_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,$MENTOR_FIRSTNAME TEXT NOT NULL,$MENTOR_LASTNAME TEXT NOT NULL,$MENTOR_PATRON TEXT NOT NULL,$MENTOR_SPECIALITY TEXT NOT NULL)"""
        val query4 =
            """CREATE TABLE $STUDENT_TABLE($STUDENT_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,$STUDENT_FIRSTNAME TEXT NOT NULL,$STUDENT_LASTNAME TEXT NOT NULL,$STUDENT_PATRON TEXT NOT NULL,$STUDENT_GROUP_ID INTEGER NOT NULL,$STUDENT_REGISTER_DATE INTEGER NOT NULL,FOREIGN KEY($STUDENT_GROUP_ID) REFERENCES $GROUP_TABLE($GROUP_ID))"""

        p0?.execSQL(query1)
        p0?.execSQL(query2)
        p0?.execSQL(query3)
        p0?.execSQL(query4)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addCourse(course: Course): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COURSE_NAME, course.name)
        contentValues.put(COURSE_DESCRIPTION, course.description)
        return database.insert(COURSE_TABLE, null, contentValues)
    }

    override fun editCourse(course: Course) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COURSE_ID, course.id)
        contentValues.put(COURSE_NAME, course.name)
        contentValues.put(COURSE_DESCRIPTION, course.description)
        database.update(
            COURSE_TABLE,
            contentValues,
            "$COURSE_ID = ?",
            arrayOf(course.id.toString())
        )
    }

    override fun getCourseById(id: Long): Course {
        val database = this.readableDatabase
        val cursor = database.query(
            COURSE_TABLE, arrayOf(COURSE_ID, COURSE_NAME, COURSE_DESCRIPTION), "$COURSE_ID = ?",
            arrayOf("$id"), null, null, null
        )
        cursor.moveToFirst()
        return Course(cursor.getLong(0), cursor.getString(1), cursor.getString(2))
    }

    override fun getAllCourses(): ArrayList<Course> {
        val list = ArrayList<Course>()
        val database = this.readableDatabase
        val query = "SELECT * FROM $COURSE_TABLE"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(0)
                val name = cursor.getString(1)
                val description = cursor.getString(2)
                val course = Course(id, name, description)
                list.add(course)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addMentor(mentor: Mentor): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTOR_FIRSTNAME, mentor.firstname)
        contentValues.put(MENTOR_LASTNAME, mentor.lastname)
        contentValues.put(MENTOR_PATRON, mentor.patron)
        contentValues.put(MENTOR_SPECIALITY, mentor.speciality)
        return database.insert(MENTOR_TABLE, null, contentValues)
    }

    override fun editMentor(mentor: Mentor) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTOR_ID, mentor.id)
        contentValues.put(MENTOR_FIRSTNAME, mentor.firstname)
        contentValues.put(MENTOR_LASTNAME, mentor.lastname)
        contentValues.put(MENTOR_PATRON, mentor.patron)
        contentValues.put(MENTOR_SPECIALITY, mentor.speciality)
        database.update(
            MENTOR_TABLE,
            contentValues,
            "$MENTOR_ID = ?",
            arrayOf(mentor.id.toString())
        )
    }

    override fun deleteMentor(mentor: Mentor) {
        val database = this.writableDatabase
        database.delete(MENTOR_TABLE, "$MENTOR_ID = ?", arrayOf(mentor.id.toString()))
    }

    override fun getAllMentor(tool:String): ArrayList<Mentor> {
        val list = ArrayList<Mentor>()
        val database = this.readableDatabase
        val query = "SELECT * FROM $MENTOR_TABLE WHERE $MENTOR_SPECIALITY = '$tool'"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(0)
                val firstName = cursor.getString(1)
                val lastName = cursor.getString(2)
                val patron = cursor.getString(3)
                val speciality = cursor.getString(4)
                val mentor = Mentor(id, firstName, lastName, patron, speciality)
                list.add(mentor)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addGroup(group: Group): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_NAME, group.groupName)
        contentValues.put(GROUP_IS_OPEN, group.groupIsOpen)
        contentValues.put(GROUP_DATE, group.groupDate)
        contentValues.put(GROUP_TYPE, group.groupType)
        contentValues.put(GROUP_COURSE_ID, group.courseId)
        contentValues.put(GROUP_MENTOR_ID, group.mentorId)
        return database.insert(GROUP_TABLE, null, contentValues)
    }

    override fun editGroup(group: Group) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_ID, group.id)
        contentValues.put(GROUP_NAME, group.groupName)
        contentValues.put(GROUP_IS_OPEN, group.groupIsOpen)
        contentValues.put(GROUP_DATE, group.groupDate)
        contentValues.put(GROUP_TYPE, group.groupType)
        contentValues.put(GROUP_COURSE_ID, group.courseId)
        contentValues.put(GROUP_MENTOR_ID, group.mentorId)
        database.update(
            GROUP_TABLE,
            contentValues,
            "$GROUP_ID = ?",
            arrayOf(group.id.toString())
        )
    }

    override fun deleteGroup(group: Group) {
        val database = this.writableDatabase
        database.delete(GROUP_TABLE, "$GROUP_ID = ?", arrayOf(group.id.toString()))
    }

    override fun startLessonInGroup(group: Group) {

    }

    override fun getGroupById(id: Long): Group {
        val database = this.readableDatabase
        val cursor = database.query(
            GROUP_TABLE, arrayOf(
                GROUP_ID, GROUP_NAME, GROUP_IS_OPEN, GROUP_COURSE_ID,
                GROUP_MENTOR_ID, GROUP_DATE, GROUP_TYPE
            ), "$GROUP_ID = ?",
            arrayOf("$id"), null, null, null
        )
        cursor.moveToFirst()
        return Group(
            id = cursor.getLong(0),
            groupName = cursor.getString(1),
            groupIsOpen = cursor.getInt(2),
            courseId = cursor.getLong(3),
            mentorId = cursor.getLong(4),
            groupDate = cursor.getString(5),
            groupType = cursor.getString(6)
        )
    }

    override fun getGroupList(selected:Long,type:Long): ArrayList<Group> {
        val list = ArrayList<Group>()
        val database = this.readableDatabase
        val query = "SELECT * FROM $GROUP_TABLE WHERE $GROUP_IS_OPEN = $selected AND $GROUP_COURSE_ID = $type"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(0)
                val groupName = cursor.getString(1)
                val groupIsOpen = cursor.getInt(2)
                val groupCourseId = cursor.getLong(3)
                val groupMentorId = cursor.getLong(4)
                val groupDate = cursor.getString(5)
                val groupType = cursor.getString(6)
                val group = Group(
                    id = id,
                    groupName = groupName,
                    groupIsOpen = groupIsOpen,
                    groupDate = groupDate,
                    groupType = groupType,
                    courseId = groupCourseId,
                    mentorId = groupMentorId
                )
                list.add(group)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getMentorGroupList(mentorId: Long): ArrayList<Group> {
        val list = ArrayList<Group>()
        val database = this.readableDatabase
        val query = "SELECT * FROM $GROUP_TABLE WHERE $GROUP_MENTOR_ID = $mentorId "
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(0)
                val groupName = cursor.getString(1)
                val groupIsOpen = cursor.getInt(2)
                val groupCourseId = cursor.getLong(3)
                val groupMentorId = cursor.getLong(4)
                val groupDate = cursor.getString(5)
                val groupType = cursor.getString(6)
                val group = Group(
                    id = id,
                    groupName = groupName,
                    groupIsOpen = groupIsOpen,
                    groupDate = groupDate,
                    groupType = groupType,
                    courseId = groupCourseId,
                    mentorId = groupMentorId
                )
                list.add(group)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addStudent(student: Student): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_FIRSTNAME, student.firstname)
        contentValues.put(STUDENT_LASTNAME, student.lastname)
        contentValues.put(STUDENT_PATRON, student.patron)
        contentValues.put(STUDENT_REGISTER_DATE, student.registerDate)
        contentValues.put(STUDENT_GROUP_ID, student.groupId)
        return database.insert(STUDENT_TABLE, null, contentValues)
    }

    override fun editStudent(student: Student) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_ID, student.id)
        contentValues.put(STUDENT_FIRSTNAME, student.firstname)
        contentValues.put(STUDENT_LASTNAME, student.lastname)
        contentValues.put(STUDENT_PATRON, student.patron)
        contentValues.put(STUDENT_REGISTER_DATE, student.registerDate)
        contentValues.put(STUDENT_GROUP_ID, student.groupId)
        database.update(
            STUDENT_TABLE,
            contentValues,
            "$STUDENT_ID = ?",
            arrayOf(student.id.toString())
        )
    }

    override fun deleteStudent(student: Student) {
        val database = this.writableDatabase
        database.delete(STUDENT_TABLE, "$STUDENT_ID = ?", arrayOf(student.id.toString()))
    }

    override fun getStudentByGroup(id: Long): ArrayList<Student> {
        val list = ArrayList<Student>()
        val database = this.readableDatabase
        val query = "SELECT * FROM $STUDENT_TABLE WHERE $STUDENT_GROUP_ID = $id"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id1 = cursor.getLong(0)
                val firstname = cursor.getString(1)
                val lastname = cursor.getString(2)
                val patron = cursor.getString(3)
                val registerDate = cursor.getString(4)
                val groupId = cursor.getLong(5)
                val student = Student(
                    id = id1,
                    firstname = firstname,
                    lastname = lastname,
                    patron = patron,
                    registerDate = registerDate,
                    groupId = groupId
                )

                list.add(student)
            } while (cursor.moveToNext())
        }
        return list
    }
}