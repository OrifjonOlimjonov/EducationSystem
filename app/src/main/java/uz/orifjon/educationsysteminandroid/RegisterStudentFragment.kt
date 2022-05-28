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
import uz.orifjon.educationsysteminandroid.databinding.FragmentRegisterStudentBinding

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
    private lateinit var binding:FragmentRegisterStudentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = FragmentRegisterStudentBinding.inflate(inflater)

            binding.tvDate.setOnClickListener {
                val datePickerDialog = DatePickerDialog(requireContext(),
                    OnDateSetListener { datePicker: DatePicker?, i: Int, i1: Int, i2: Int ->
                        Toast.makeText(
                            requireContext(),
                            "$i2 $i1 $i",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.tvDate.setText("$i2/$i1/$i")
                    }, 2022, 3, 6
                )
                datePickerDialog.show()
            }

        return binding.root
    }

}