package uz.orifjon.educationsysteminandroid.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mentor(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    var firstname:String,
    var lastname:String,
    var patron:String,
    var speciality:String
    )