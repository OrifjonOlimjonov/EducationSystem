package uz.orifjon.educationsysteminandroid.fragments

import android.annotation.SuppressLint
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
import uz.orifjon.educationsysteminandroid.R
import uz.orifjon.educationsysteminandroid.adapters.AdapterStudentRV
import uz.orifjon.educationsysteminandroid.database.AppDatabase
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.DeleteDialogBinding
import uz.orifjon.educationsysteminandroid.databinding.FragmentGroupViewBinding
import uz.orifjon.educationsysteminandroid.models.Student

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GroupViewFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentGroupViewBinding

    //    private lateinit var mySqliteHelper: MySqliteHelper
    private lateinit var adapter: AdapterStudentRV
    private lateinit var list: ArrayList<Student>

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupViewBinding.inflate(inflater)
        //mySqliteHelper = MySqliteHelper(requireContext())
        val groupId = arguments?.getLong("id")
        // list = mySqliteHelper.getStudentByGroup(groupId!!)
        list = AppDatabase.getDatabase(requireContext()).studentDao()
            .getGroupByStudent(groupId!!) as ArrayList<Student>
//        val group = mySqliteHelper.getGroupById(groupId)
        val group = AppDatabase.getDatabase(requireContext()).groupDao().getGroupById(groupId)

        binding.groupName.text = group.groupName
        binding.groupCount.text = "O'quvchilar soni: ${list.size} ta"
        binding.groupTime.text = "Vaqti: ${group.groupDate.split(" ").take(2)[0]}"
        adapter = AdapterStudentRV(list, { student, i ->
            val alertDialog = AlertDialog.Builder(requireContext())
            val binding = DeleteDialogBinding.inflate(layoutInflater)
            alertDialog.setView(binding.root)
            val alertDialog1 = alertDialog.create()
            alertDialog1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.btnYes.setOnClickListener {
                AppDatabase.getDatabase(requireContext()).studentDao().deleteStudent(student)
                //mySqliteHelper.deleteStudent(student)
                list.removeAt(i)
                adapter.notifyItemRemoved(i)
                adapter.notifyItemRangeChanged(i, list.size)
                this.binding.groupCount.text = "O'quvchilar soni: ${list.size} ta"
                alertDialog1.dismiss()
            }
            binding.btnNo.setOnClickListener {
                alertDialog1.dismiss()
            }
            alertDialog1.show()
        }, { student, i ->
            val bundle = Bundle()
            bundle.putSerializable("student", student)
            bundle.putString("date", student.registerDate)
            findNavController().navigate(R.id.editStudentGroupFragment, bundle)
        })
        if (list.size == 0) {
            Toast.makeText(requireContext(), "O'quvchilar mavjud emas!!", Toast.LENGTH_SHORT).show()
        }
        binding.btnStartGroup.setOnClickListener {
            if (list.size > 4) {
                group.groupIsOpen = 1
                AppDatabase.getDatabase(requireContext()).groupDao().editGroup(group)
                //mySqliteHelper.editGroup(group)
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Guruh hali to'lmagan!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addButton -> {
                    val bundle = Bundle()
                    bundle.putLong("id", groupId)
                    findNavController().navigate(R.id.addGroupStudentFragment, bundle)
                }
            }
            true
        }

        binding.rv.adapter = adapter
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}