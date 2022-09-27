package uz.orifjon.educationsysteminandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.database.AppDatabase
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentAddNewMentorBinding
import uz.orifjon.educationsysteminandroid.models.Mentor

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddNewMentorFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentAddNewMentorBinding
    //private lateinit var mySqliteHelper: MySqliteHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val speciality = arguments?.getString("tool")
        binding = FragmentAddNewMentorBinding.inflate(inflater)
        //mySqliteHelper = MySqliteHelper(requireContext())
        binding.btnAddMentor.setOnClickListener {
            if (binding.tvFirstName.text.isNotEmpty() && binding.tvLastName.text.isNotEmpty() && binding.tvPatron.text.isNotEmpty()) {
                val firstName = binding.tvFirstName.text.toString()
                val lastName = binding.tvLastName.text.toString()
                val patron = binding.tvPatron.text.toString()
                val mentor = Mentor(
                    firstname = firstName,
                    lastname = lastName,
                    patron = patron,
                    speciality = speciality!!
                )
                //mySqliteHelper.addMentor(mentor)
                AppDatabase.getDatabase(requireContext()).mentorDao().addMentor(mentor)
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

}