package uz.orifjon.educationsysteminandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.orifjon.educationsysteminandroid.databinding.FragmentAddCourseBinding

private const val ARG_PARAM1 = "param1"


class AddCourseFragment : Fragment() {
    private var param1: String? = null
    private lateinit var binding: FragmentAddCourseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCourseBinding.inflate(inflater)


        return binding.root
    }

}