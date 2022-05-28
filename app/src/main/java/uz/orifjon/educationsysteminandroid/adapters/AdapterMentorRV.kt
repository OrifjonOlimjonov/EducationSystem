package uz.orifjon.educationsysteminandroid.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.orifjon.educationsysteminandroid.databinding.RvMentorItemBinding
import uz.orifjon.educationsysteminandroid.models.Mentor

class AdapterMentorRV(var list: ArrayList<Mentor>) : RecyclerView.Adapter<AdapterMentorRV.VH>() {

    inner class VH(var binding: RvMentorItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(mentor: Mentor, position: Int) {
            binding.tvName.text = "${mentor.firstname} ${mentor.lastname}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(RvMentorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}