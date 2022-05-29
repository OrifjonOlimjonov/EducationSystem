package uz.orifjon.educationsysteminandroid

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.orifjon.educationsysteminandroid.adapters.AdapterGroupRV
import uz.orifjon.educationsysteminandroid.adapters.SpinnerMentorAdapter
import uz.orifjon.educationsysteminandroid.adapters.SpinnerTimeAdapter
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.AddCourseDialogBinding
import uz.orifjon.educationsysteminandroid.databinding.EditGroupDialogBinding
import uz.orifjon.educationsysteminandroid.databinding.FragmentViewPager2Binding
import uz.orifjon.educationsysteminandroid.models.Course
import uz.orifjon.educationsysteminandroid.models.Group
import uz.orifjon.educationsysteminandroid.models.Mentor

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewPager2Fragment : Fragment() {
    private var param1: Long? = null
    private var param2: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getLong(ARG_PARAM1)
            param2 = it.getLong(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentViewPager2Binding
    private lateinit var mySqliteHelper: MySqliteHelper
    private lateinit var adapter: AdapterGroupRV
    private lateinit var list: ArrayList<Group>
    private lateinit var listCount: ArrayList<Int>
    private lateinit var adapterMentor: SpinnerMentorAdapter
    private lateinit var mentorList: ArrayList<Mentor>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listCount = ArrayList()
        binding = FragmentViewPager2Binding.inflate(inflater)
        mySqliteHelper = MySqliteHelper(requireContext())
        list = mySqliteHelper.getGroupList(param1!!, param2!!)
        mentorList = mySqliteHelper.getAllMentor(mySqliteHelper.getCourseById(param2!!).name)
        adapterMentor = SpinnerMentorAdapter(mentorList)
        if (list.size == 0) {
            Toast.makeText(requireContext(), "Guruhlar mavjud emas!", Toast.LENGTH_SHORT).show()
        }
        for (i in 0 until list.size) {
            listCount.add(mySqliteHelper.getStudentByGroup(list[i].id).size)
        }
        if (list.isNotEmpty() && listCount.isNotEmpty()) {
            adapter = AdapterGroupRV(list, listCount, { group, i ->
                //TODO: REMOVE group
                mySqliteHelper.deleteGroup(group)
                list.removeAt(i)
                adapter.notifyItemRemoved(i)
                adapter.notifyItemRangeChanged(i, list.size)
            }, { group, i ->
                // TODO: EDIT group
                val list = arrayListOf("09:00-11:00", "11:00-14:00", "14:00-16:00", "19:00-21:00")
                val alertDialog = AlertDialog.Builder(requireContext())
                val binding = EditGroupDialogBinding.inflate(layoutInflater)
                alertDialog.setView(binding.root)
                val alertDialog1 = alertDialog.create()
                binding.groupName.setText(group.groupName)
                binding.spinnerMentor.adapter = adapterMentor
                binding.spinnerTime.adapter = SpinnerTimeAdapter()
                for (w in 0 until mentorList.size) {
                    if (group.mentorId == mentorList[w].id) {
                        binding.spinnerMentor.setSelection(w)
                        break
                    }
                }
                val take = group.groupDate.split(" ").take(2)
                for (q in 0 until 4) {
                    if (take[0] == list[q]) {
                        binding.spinnerTime.setSelection(q)
                        break
                    }
                }
                alertDialog1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                binding.btnSave.setOnClickListener {
                    val groupName = binding.groupName.text.toString()
                    val groupMentor = binding.spinnerMentor.selectedItemPosition
                    val groupTime = binding.spinnerTime.selectedItem.toString()
                    val groupNew = Group(
                        groupName = groupName,
                        groupIsOpen = 0,
                        groupType = mySqliteHelper.getCourseById(param2!!).name,
                        groupDate = "$groupTime ${take[1]}",
                        courseId = group.courseId,
                        mentorId = mentorList[groupMentor].id
                    )
                    mySqliteHelper.editGroup(groupNew)
                    this.list[i] = groupNew
                    adapter.notifyItemChanged(i)
                    alertDialog1.dismiss()
                }
                binding.btnCancel.setOnClickListener {
                    alertDialog1.dismiss()
                }


                alertDialog1.show()
            }, { group, i ->
                // TODO: View
                val bundle = Bundle()
                bundle.putLong("id", group.id)


                findNavController().navigate(R.id.groupViewFragment)
            })
            binding.rvGroup.adapter = adapter
        }



        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Long, param2: Long) =
            ViewPager2Fragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                    putLong(ARG_PARAM2, param2)
                }
            }
    }
}