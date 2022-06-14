package uz.orifjon.educationsysteminandroid

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.database.AppDatabase
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentAddGroupBinding
import uz.orifjon.educationsysteminandroid.databinding.FragmentAddGroupStudentBinding
import uz.orifjon.educationsysteminandroid.models.Student

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddGroupStudentFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentAddGroupStudentBinding

    //  private lateinit var mySqliteHelper: MySqliteHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddGroupStudentBinding.inflate(inflater, container, false)
        // mySqliteHelper = MySqliteHelper(requireContext())
        val groupId = arguments?.getLong("id")

        binding.btnAddStudent.setOnClickListener {
            val firsName = binding.tvFirstName.text.toString().trim()
            val lastName = binding.tvLastName.text.toString().trim()
            val patron = binding.tvPatron.text.toString().trim()
            val date = binding.tvDate.text.toString().trim()
            if (firsName.isNotEmpty() && lastName.isNotEmpty() && patron.isNotEmpty() && date.isNotEmpty()) {
                val student = Student(
                    firstname = firsName,
                    lastname = lastName,
                    patron = patron,
                    registerDate = date,
                    groupId = groupId!!
                )
                //  mySqliteHelper.addStudent(student)
                AppDatabase.getDatabase(requireContext()).studentDao().addStudent(student)
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Maydonlarni to'ldiring!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.tvDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { datePicker: DatePicker?, i: Int, i1: Int, i2: Int ->
                    binding.tvDate.setText("$i2/$i1/$i")
                }, 2022, 3, 6
            )
            datePickerDialog.show()
        }
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddGroupStudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}