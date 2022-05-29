package uz.orifjon.educationsysteminandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.orifjon.educationsysteminandroid.databinding.SpinnerMentorItemBinding

class SpinnerWeekAdapter :BaseAdapter() {
    val list = arrayListOf("Toq","Juft")

    override fun getCount(): Int = 2

    override fun getItem(p0: Int): Any = list[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding: SpinnerMentorItemBinding = if (p1 == null) {
            SpinnerMentorItemBinding.inflate(LayoutInflater.from(p2?.context), p2, false)
        }else{
            SpinnerMentorItemBinding.bind(p1)
        }
        binding.spinText.text = list[p0]

        return binding.root
    }
}