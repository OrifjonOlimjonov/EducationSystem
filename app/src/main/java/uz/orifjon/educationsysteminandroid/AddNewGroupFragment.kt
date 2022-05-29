package uz.orifjon.educationsysteminandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.adapters.SpinnerMentorAdapter
import uz.orifjon.educationsysteminandroid.adapters.SpinnerTimeAdapter
import uz.orifjon.educationsysteminandroid.adapters.SpinnerWeekAdapter
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentAddNewGroupBinding
import uz.orifjon.educationsysteminandroid.models.Group

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddNewGroupFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentAddNewGroupBinding
    private lateinit var adapterWeek: SpinnerWeekAdapter
    private lateinit var adapterTime: SpinnerTimeAdapter
    private lateinit var adapter:SpinnerMentorAdapter
    private lateinit var mySqliteHelper: MySqliteHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewGroupBinding.inflate(inflater, container, false)
        mySqliteHelper = MySqliteHelper(requireContext())
        val courseName = arguments?.getString("tool")
        val courseId = arguments?.getLong("courseId")
        adapterTime = SpinnerTimeAdapter()
        adapterWeek = SpinnerWeekAdapter()
        binding.spinnerTime.adapter = adapterTime
        binding.spinnerWeek.adapter = adapterWeek
        val list = mySqliteHelper.getAllMentor(courseName!!)
        adapter = SpinnerMentorAdapter(list)
        binding.spinnerMentor.adapter = adapter
        binding.btnSaveGroup.setOnClickListener {
            if (binding.tvGroupName.text.isNotEmpty()) {
                if (binding.spinnerMentor.selectedItemPosition >= 0) {
                    if (binding.spinnerTime.selectedItemPosition >= 0) {
                        if (binding.spinnerWeek.selectedItemPosition >= 0) {
                            val groupName= binding.tvGroupName.text.toString()
                            val groupDate = "${binding.spinnerTime.selectedItem} ${binding.spinnerWeek.selectedItem}"
                            val group = Group(groupName = groupName, groupIsOpen = 0, groupType = courseName, groupDate = groupDate, courseId = courseId!!, mentorId = list[binding.spinnerMentor.selectedItemPosition].id)
                            mySqliteHelper.addGroup(group)
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Juftlikni tanlang!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Vaqtini tanlang!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Mentorni tanlang!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Guruh nomini kiriting!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewGroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}