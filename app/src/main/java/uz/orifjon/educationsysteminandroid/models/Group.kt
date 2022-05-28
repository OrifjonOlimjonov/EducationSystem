package uz.orifjon.educationsysteminandroid.models

data class Group(var id:Long = 0,var groupName:String,var groupIsOpen:Int,var groupDate:String,var groupType:String,var courseId:Int,var mentorId:Int)
