package uz.orifjon.educationsysteminandroid.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.orifjon.educationsysteminandroid.ViewPager2Fragment

class ViewPager2Adapter(fragment: FragmentManager, lifecycle: Lifecycle, var id:Long) :
    FragmentStateAdapter(fragment, lifecycle) {
    val list = arrayListOf("Ochilgan guruhlar", "Ochilayotgan guruhlar")

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =  ViewPager2Fragment.newInstance(((position + 1) % 2).toLong(),id)

}