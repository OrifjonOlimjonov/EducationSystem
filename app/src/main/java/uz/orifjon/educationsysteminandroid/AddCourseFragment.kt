package uz.orifjon.educationsysteminandroid

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.AddCourseDialogBinding
import uz.orifjon.educationsysteminandroid.databinding.FragmentAddCourseBinding
import uz.orifjon.educationsysteminandroid.models.Course

private const val ARG_PARAM1 = "param1"


class AddCourseFragment : Fragment() {
    private var param1: String? = null
    private lateinit var binding: FragmentAddCourseBinding
    private lateinit var mySqliteHelper: MySqliteHelper
    private lateinit var list: ArrayList<Course>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCourseBinding.inflate(inflater)
        mySqliteHelper = MySqliteHelper(requireContext())

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        list = mySqliteHelper.getAllCourses()
        if (list.isEmpty()) {
            Toast.makeText(requireContext(), "Ma'lumotlar mavjud emas!!", Toast.LENGTH_SHORT).show()
        }else{

        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addButton -> {
                    val alertDialog = AlertDialog.Builder(requireContext())
                    val addCourseDialogBinding = AddCourseDialogBinding.inflate(layoutInflater)
                    alertDialog.setView(addCourseDialogBinding.root)
                    val alertDialog1 = alertDialog.create()
                    alertDialog1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    addCourseDialogBinding.btnSave.setOnClickListener {
                        val courseName = addCourseDialogBinding.courseName.toString()
                        val courseInfo = addCourseDialogBinding.courseInfo.toString()
                        val course = Course(name = courseName, description = courseInfo)
                        list.add(course)
                        mySqliteHelper.addCourse(course)
                    }
                    addCourseDialogBinding.btnCancel.setOnClickListener {
                        alertDialog1.dismiss()
                    }


                    alertDialog1.show()
                }
            }
            true
        }

        return binding.root
    }

}