package uz.orifjon.educationsysteminandroid.database

import uz.orifjon.educationsysteminandroid.models.Course
import uz.orifjon.educationsysteminandroid.models.Group
import uz.orifjon.educationsysteminandroid.models.Mentor
import uz.orifjon.educationsysteminandroid.models.Student

interface DatabaseService {

    // TODO: course
    fun addCourse(course: Course):Long

    fun editCourse(course: Course)

    fun getCourseById(id:Long):Course

    fun getAllCourses():ArrayList<Course>

    //TODO: mentor

    fun addMentor(mentor: Mentor):Long

    fun editMentor(mentor: Mentor)

    fun deleteMentor(mentor: Mentor)

    fun getAllMentor(tool:String):ArrayList<Mentor>

    // TODO: group

    fun addGroup(group:Group):Long

    fun editGroup(group: Group)

    fun deleteGroup(group: Group)

    fun startLessonInGroup(group: Group)

    fun getGroupById(id: Long):Group

    fun getGroupList(selected:Int):ArrayList<Group>

    fun getMentorGroupList(mentorId:Long):ArrayList<Group>

    // TODO: student

    fun addStudent(student: Student):Long

    fun editStudent(student: Student)

    fun deleteStudent(student: Student)

    fun getStudentByGroup(id: Long):ArrayList<Student>

}