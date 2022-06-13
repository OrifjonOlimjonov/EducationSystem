package uz.orifjon.educationsysteminandroid.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(foreignKeys = [
    ForeignKey(
        entity = Group::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("group_id"),
        onDelete = ForeignKey.CASCADE
    )
])
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var firstname:String,
    var lastname:String,
    var patron:String,
    @ColumnInfo(name = "register_date")
    var registerDate:String,
    @ColumnInfo(name = "group_id")
    var groupId:Long
    ):Serializable