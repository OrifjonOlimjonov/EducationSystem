package uz.orifjon.educationsysteminandroid.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fragment: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment, lifecycle) {
    val list = arrayListOf("Ochilgan guruhlar", "Ochilayotgan guruhlar")

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
                    return Fragment()
    }
}