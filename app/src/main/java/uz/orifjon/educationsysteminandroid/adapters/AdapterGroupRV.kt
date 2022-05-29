package uz.orifjon.educationsysteminandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.orifjon.educationsysteminandroid.databinding.RvGroupItemBinding
import uz.orifjon.educationsysteminandroid.models.Group

class AdapterGroupRV(var list: ArrayList<Group>,var listCount:ArrayList<Int>,var onDelete:(Group,Int)->Unit,var onEdit:(Group,Int)->Unit,var onView:(Group,Int)->Unit) : RecyclerView.Adapter<AdapterGroupRV.VH>() {

    inner class VH(var binding: RvGroupItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(group: Group, position: Int) {
            binding.tvName.text = group.groupName
            binding.tvTime.text = "O'quvchilar soni:${listCount[position]} ta"
            binding.btnDelete.setOnClickListener { onDelete(group,position) }
            binding.btnEdit.setOnClickListener { onEdit(group,position) }
            binding.btnView.setOnClickListener { onView(group,position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RvGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.onBind(list[position], position)

    override fun getItemCount(): Int = list.size
}