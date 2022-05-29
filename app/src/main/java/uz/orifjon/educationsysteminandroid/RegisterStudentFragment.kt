package uz.orifjon.educationsysteminandroid

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.adapters.SpinnerGroupAdapter
import uz.orifjon.educationsysteminandroid.adapters.SpinnerMentorAdapter
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentRegisterStudentBinding
import uz.orifjon.educationsysteminandroid.models.Group
import uz.orifjon.educationsysteminandroid.models.Mentor
import uz.orifjon.educationsysteminandroid.models.Student

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegisterStudentFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentRegisterStudentBinding
    private lateinit var spinnerMentor: SpinnerMentorAdapter
    private lateinit var spinnerGroup: SpinnerGroupAdapter
    private lateinit var listMentor: ArrayList<Mentor>
    private lateinit var listGroup: ArrayList<Group>
    private lateinit var mySqliteHelper: MySqliteHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterStudentBinding.inflate(inflater)
        mySqliteHelper = MySqliteHelper(requireContext())
        val name = arguments?.getString("name")
        listMentor = mySqliteHelper.getAllMentor(name!!)
        spinnerMentor = SpinnerMentorAdapter(listMentor)
        binding.spinnerMentor.adapter = spinnerMentor
        if (listMentor.isNotEmpty()) {
            listGroup =
                mySqliteHelper.getMentorGroupList(listMentor[binding.spinnerMentor.selectedItemPosition].id)
            spinnerGroup = SpinnerGroupAdapter(listGroup)
            binding.spinnerGroup.adapter = spinnerGroup
        } else {
            Toast.makeText(requireContext(), "Mentorlar mavjud emas!", Toast.LENGTH_SHORT).show()
        }
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        binding.tvDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { datePicker: DatePicker?, i: Int, i1: Int, i2: Int ->
                    binding.tvDate.setText("$i2/$i1/$i")
                }, 2022, 3, 6
            )
            datePickerDialog.show()
        }
        binding.btnAddStudent.setOnClickListener {
            if (binding.spinnerMentor.selectedItemPosition >= 0) {
                if (binding.spinnerGroup.selectedItemPosition >= 0) {
                    if (binding.tvPatron.text.isEmpty() && binding.tvFirstName.text.isEmpty() && binding.tvLastName.text.isEmpty() && binding.tvDate.text.isEmpty()) {
                        Toast.makeText(requireContext(), "Maydonlarni to'ldiring!!", Toast.LENGTH_SHORT).show()
                    } else {
                        val firstName = binding.tvFirstName.text.toString()
                        val lastName = binding.tvLastName.text.toString()
                        val patron = binding.tvPatron.text.toString()
                        val date = binding.tvDate.text.toString()
                        val group = binding.spinnerGroup.selectedItemId.toInt() + 1
                        val student = Student(
                            firstname = firstName,
                            lastname = lastName,
                            patron = patron,
                            registerDate = date,
                            groupId = group.toLong()
                        )
                        mySqliteHelper.addStudent(student)
                        findNavController().popBackStack()
                    }
                } else {
                    Toast.makeText(requireContext(), "Guruhni tanlang!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Mentorni tanlang!", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

}