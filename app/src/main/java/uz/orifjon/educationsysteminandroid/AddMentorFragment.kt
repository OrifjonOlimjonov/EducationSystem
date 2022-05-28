package uz.orifjon.educationsysteminandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.adapters.AdapterRV
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentAddMentorBinding
import uz.orifjon.educationsysteminandroid.models.Course

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddMentorFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentAddMentorBinding
    private lateinit var mySqliteHelper: MySqliteHelper
    private lateinit var adapter: AdapterRV
    private lateinit var list: ArrayList<Course>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMentorBinding.inflate(inflater)
        mySqliteHelper = MySqliteHelper(requireContext())
        list = mySqliteHelper.getAllCourses()
        adapter = AdapterRV(list) { course, i ->
            val bundle = Bundle()
            bundle.putLong("id", course.id)
            bundle.putInt("index", i)
            bundle.putString("toolbar",course.name)
            findNavController().navigate(R.id.mentorInfoFragment, bundle)
        }
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.rv.adapter = adapter
        return binding.root
    }

}