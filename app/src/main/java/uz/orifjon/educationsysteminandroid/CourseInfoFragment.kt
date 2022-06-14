package uz.orifjon.educationsysteminandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.database.AppDatabase
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentCourseInfoBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CourseInfoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentCourseInfoBinding
  //  private lateinit var mySqliteHelper: MySqliteHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseInfoBinding.inflate(inflater)
       // mySqliteHelper = MySqliteHelper(requireContext())
        val id = arguments?.getLong("id")
        val course = AppDatabase.getDatabase(requireContext()).courseDao().getByIdCourse(id!!)
            //mySqliteHelper.getCourseById(id!!)
        binding.toolbar.title = course.name
        binding.textInfo.text = course.description
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAddStudent.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name",course.name)
            findNavController().navigate(R.id.registerStudentFragment,bundle)
        }
        return binding.root
    }

}