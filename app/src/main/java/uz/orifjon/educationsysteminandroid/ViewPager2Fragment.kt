package uz.orifjon.educationsysteminandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import uz.orifjon.educationsysteminandroid.adapters.AdapterGroupRV
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentViewPager2Binding
import uz.orifjon.educationsysteminandroid.models.Group

private const val ARG_PARAM1 = "param1"

class ViewPager2Fragment : Fragment() {
    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    private lateinit var binding: FragmentViewPager2Binding
    private lateinit var mySqliteHelper: MySqliteHelper
    private lateinit var adapter: AdapterGroupRV
    private lateinit var list: ArrayList<Group>
    private lateinit var listCount: ArrayList<Int>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listCount = ArrayList()
        binding = FragmentViewPager2Binding.inflate(inflater)
        mySqliteHelper = MySqliteHelper(requireContext())
        list = mySqliteHelper.getGroupList(param1!!)

        if (list.size == 0) {
            Toast.makeText(requireContext(), "Guruhlar mavjud emas!", Toast.LENGTH_SHORT).show()
        }
        for (i in 0 until list.size) {
            listCount.add(mySqliteHelper.getStudentByGroup(list[i].id).size)
        }
        if (list.isNotEmpty() && listCount.isNotEmpty()) {
            adapter = AdapterGroupRV(list, listCount,{ group, i ->
                mySqliteHelper.deleteGroup(group)
                list.removeAt(i)
                adapter.notifyItemRemoved(i)
                adapter.notifyItemRangeChanged(i, list.size)
            },{group, i ->

            },{group, i ->

            })
            binding.rvGroup.adapter = adapter
        }



        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            ViewPager2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}