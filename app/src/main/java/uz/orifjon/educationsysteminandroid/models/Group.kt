package uz.orifjon.educationsysteminandroid.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "group_1", foreignKeys = [ForeignKey(
        entity = Mentor::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("mentor_id")
    ), ForeignKey(
        entity = Course::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("course_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Group(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "group_name")
    var groupName: String,
    @ColumnInfo(name = "group_is_open")
    var groupIsOpen: Int,
    @ColumnInfo(name = "group_date")
    var groupDate: String,
    @ColumnInfo(name = "group_type")
    var groupType: String,
    @ColumnInfo(name = "course_id")
    var courseId: Long,
    @ColumnInfo(name = "mentor_id")
    var mentorId: Long
)
