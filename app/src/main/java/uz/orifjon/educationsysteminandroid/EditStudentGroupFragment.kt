package uz.orifjon.educationsysteminandroid

import android.annotation.SuppressLint
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
import uz.orifjon.educationsysteminandroid.databinding.FragmentEditStudentGroupBinding
import uz.orifjon.educationsysteminandroid.models.Student

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditStudentGroupFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentEditStudentGroupBinding
   // private lateinit var mySqliteHelper:MySqliteHelper
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditStudentGroupBinding.inflate(inflater)
        //mySqliteHelper = MySqliteHelper(requireContext())
        val student = arguments?.getSerializable("student") as Student
        binding.tvFirstName.setText(student.firstname)
        binding.tvLastName.setText(student.lastname)
        binding.tvPatron.setText(student.patron)
        binding.tvDate.setText(student.registerDate)
        binding.btnAddStudent.setOnClickListener {
            val firsName = binding.tvFirstName.text.toString()
            val lastName = binding.tvLastName.text.toString()
            val patron = binding.tvPatron.text.toString()
            val date = binding.tvDate.text.toString()
            if (firsName.isNotEmpty() && lastName.isNotEmpty() && patron.isNotEmpty() && date.isNotEmpty()) {
                val student = Student(
                    firstname = firsName,
                    lastname = lastName,
                    patron = patron,
                    registerDate = date,
                    groupId = student.groupId
                )
                //mySqliteHelper.addStudent(student)
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

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditStudentGroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}