package uz.orifjon.educationsysteminandroid.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.orifjon.educationsysteminandroid.databinding.SpinnerMentorItemBinding
import uz.orifjon.educationsysteminandroid.models.Group
import uz.orifjon.educationsysteminandroid.models.Mentor

class SpinnerGroupAdapter(var list: ArrayList<Group>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Group = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val binding: SpinnerMentorItemBinding = if (p1 == null) {
            SpinnerMentorItemBinding.inflate(LayoutInflater.from(p2?.context), p2, false)
        } else {
            SpinnerMentorItemBinding.bind(p1)
        }
        binding.spinText.text = list[position].groupName
        return binding.root
    }
}