package uz.orifjon.educationsysteminandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.orifjon.educationsysteminandroid.adapters.ViewPager2Adapter
import uz.orifjon.educationsysteminandroid.databinding.FragmentGroupInfoBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GroupInfoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentGroupInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupInfoBinding.inflate(inflater)
        val tool = arguments?.getString("tool")
        binding.toolbar.title = tool
        val list = arrayListOf("Ochilgan guruhlar", "Ochilayotgan guruhlar")
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        val adapterViewPager2 =ViewPager2Adapter(childFragmentManager,lifecycle)
        binding.viewPager2.adapter = adapterViewPager2
        binding.toolbar.menu.setGroupVisible(0,false)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = list[position]
        }.attach()
        binding.tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    binding.toolbar.menu.setGroupVisible(0,false)
                }else{
                    binding.toolbar.menu.setGroupVisible(0,true)
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.addButton->{

                }
            }

            true
        }
        return binding.root
    }


}