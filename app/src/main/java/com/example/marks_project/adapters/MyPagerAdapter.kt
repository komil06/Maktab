package com.example.marks_project.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.marks_project.database.entity.Group
import com.example.marks_project.fragments.StudentListFragment

class MyPagerAdapter(fa: FragmentActivity, val list: MutableList<Group>, var teacherId: Int, var subjectId: Int): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return StudentListFragment.newInstance(list[position].groupId, teacherId = teacherId, subjectId = subjectId)
    }
}
