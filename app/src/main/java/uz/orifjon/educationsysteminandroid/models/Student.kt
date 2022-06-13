package uz.orifjon.educationsysteminandroid.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
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