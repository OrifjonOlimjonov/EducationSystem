package uz.orifjon.educationsysteminandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentAddCourseBinding
import uz.orifjon.educationsysteminandroid.models.Course

private const val ARG_PARAM1 = "param1"


class AddCourseFragment : Fragment() {
    private var param1: String? = null
    private lateinit var binding: FragmentAddCourseBinding
    private lateinit var mySqliteHelper: MySqliteHelper
    private lateinit var list:ArrayList<Course>
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
        if(list.isEmpty()){
            Toast.makeText(requireContext(), "Ma'lumotlar mavjud emas!!", Toast.LENGTH_SHORT).show()
        }
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.addButton->{

                }
            }
            true
        }

        return binding.root
    }

}