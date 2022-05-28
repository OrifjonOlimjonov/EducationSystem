package uz.orifjon.educationsysteminandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.orifjon.educationsysteminandroid.databinding.ItemBinding
import uz.orifjon.educationsysteminandroid.models.Course
import uz.orifjon.educationsysteminandroid.models.Group

class AdapterRVGroup(var list: ArrayList<Group>, var onItemClick:(Group, Int)->Unit) : RecyclerView.Adapter<AdapterRVGroup.VH>() {

    inner class VH(var item: ItemBinding) : RecyclerView.ViewHolder(item.root) {

        fun onBind(group: Group, position: Int) {
            item.tvCourseName.text = group.groupName
            itemView.setOnClickListener {
                onItemClick(group,position)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}