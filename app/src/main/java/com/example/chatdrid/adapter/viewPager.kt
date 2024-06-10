package com.example.chatdrid.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class viewPager(
    private val context: Context,
    fm:FragmentManager?,
    val list:ArrayList<Fragment>
) :FragmentPagerAdapter(fm!!){
    override fun getCount(): Int {
       return 2
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }
    companion object{
        val TAB_TITLES= arrayOf("Chats","Channels")
    }

}