package uz.orifjon.educationsysteminandroid.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.orifjon.educationsysteminandroid.databinding.RvMentorItemBinding
import uz.orifjon.educationsysteminandroid.models.Mentor
import uz.orifjon.educationsysteminandroid.models.Student

class AdapterStudentRV(var list: ArrayList<Student>, var onDelete:(Student, Int)->Unit, var onEdit:(Student, Int)->Unit) : RecyclerView.Adapter<AdapterStudentRV.VH>() {

    inner class VH(var binding: RvMentorItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(student: Student, position: Int) {
            binding.tvName.text = "${student.firstname} ${student.lastname}"
            binding.tvPatron.text = student.patron
            binding.btnDelete.setOnClickListener {
                onDelete(student,position)
            }
            binding.btnEdit.setOnClickListener {
                onEdit(student,position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(RvMentorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}