package uz.orifjon.educationsysteminandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.orifjon.educationsysteminandroid.databinding.ItemBinding
import uz.orifjon.educationsysteminandroid.models.Course

class AdapterRV(var list: ArrayList<Course>) : RecyclerView.Adapter<AdapterRV.VH>() {

    inner class VH(var item: ItemBinding) : RecyclerView.ViewHolder(item.root) {

        fun onBind(course: Course, position: Int) {
            item.tvCourseName.text = course.name
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