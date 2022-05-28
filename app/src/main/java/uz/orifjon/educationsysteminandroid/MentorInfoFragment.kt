package uz.orifjon.educationsysteminandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.adapters.AdapterMentorRV
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentMentorInfoBinding
import uz.orifjon.educationsysteminandroid.models.Mentor

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MentorInfoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentMentorInfoBinding
    private lateinit var adapter: AdapterMentorRV
    private lateinit var mySqliteHelper: MySqliteHelper
    private lateinit var list: ArrayList<Mentor>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorInfoBinding.inflate(inflater)
        mySqliteHelper = MySqliteHelper(requireContext())
        val tool = arguments?.getString("toolbar")
        binding.toolbar.title = tool
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        list = mySqliteHelper.getAllMentor()
        adapter = AdapterMentorRV(list)
        binding.rv.adapter = adapter
        return binding.root
    }

}