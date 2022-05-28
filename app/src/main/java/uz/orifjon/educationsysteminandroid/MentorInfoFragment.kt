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
import uz.orifjon.educationsysteminandroid.adapters.AdapterMentorRV
import uz.orifjon.educationsysteminandroid.database.MySqliteHelper
import uz.orifjon.educationsysteminandroid.databinding.FragmentMentorInfoBinding
import uz.orifjon.educationsysteminandroid.databinding.MentorEditDialogBinding
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
        list = mySqliteHelper.getAllMentor(tool!!)
        
        if(list.size == 0){
            Toast.makeText(requireContext(), "Mentorlar mavjud emas!!", Toast.LENGTH_SHORT).show()
        }
        adapter = AdapterMentorRV(list, { mentor, i ->
            list.removeAt(i)
            mySqliteHelper.deleteMentor(mentor)
            adapter.notifyItemRemoved(i)
            adapter.notifyItemRangeChanged(i, list.size)
        }, { mentor, i ->
            val alertDialog = AlertDialog.Builder(requireContext())
            val binding = MentorEditDialogBinding.inflate(layoutInflater)
            alertDialog.setView(binding.root)
            val alertDialog1 = alertDialog.create()
            alertDialog1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.mentorName.setText(mentor.firstname)
            binding.mentorSurname.setText(mentor.lastname)
            binding.mentorPatron.setText(mentor.patron)

            binding.btnSave.setOnClickListener {
                if (binding.mentorName.text.isNotEmpty() && binding.mentorPatron.text.isNotEmpty() && binding.mentorSurname.text.isNotEmpty()) {
                    val firstName = binding.mentorName.text.toString()
                    val lastName = binding.mentorSurname.text.toString()
                    val patron = binding.mentorPatron.text.toString()

                    val mentor = Mentor(
                        id = mentor.id,
                        firstname = firstName,
                        lastname = lastName,
                        patron = patron,
                        speciality = tool!!
                    )
                    mySqliteHelper.editMentor(mentor)
                    list[i] = mentor
                    alertDialog1.dismiss()
                    adapter.notifyItemChanged(i)
                }

            }
            binding.btnCancel.setOnClickListener {
                alertDialog1.dismiss()
            }
            alertDialog1.show()
        })
        binding.rv.adapter = adapter

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addButton -> {
                    val bundle = Bundle()
                    bundle.putString("tool", tool)
                    findNavController().navigate(R.id.addNewMentorFragment, bundle)
                }
            }
            true
        }
        return binding.root
    }

}