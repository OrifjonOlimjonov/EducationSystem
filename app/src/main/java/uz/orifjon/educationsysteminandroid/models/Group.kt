package uz.orifjon.educationsysteminandroid.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    @ColumnInfo(name = "group_name")
    var groupName:String,
    @ColumnInfo(name = "group_is_open")
    var groupIsOpen:Int,
    @ColumnInfo(name = "group_date")
    var groupDate:String,
    @ColumnInfo(name = "group_type")
    var groupType:String,
    @ColumnInfo(name = "course_id")
    var courseId:Long,
    @ColumnInfo(name = "mentor_id")
    var mentorId:Long)
