package uz.orifjon.educationsysteminandroid.models

import java.io.Serializable

data class Student(val id: Long = 0,var firstname:String,var lastname:String,var patron:String,var registerDate:String,var groupId:Long):Serializable