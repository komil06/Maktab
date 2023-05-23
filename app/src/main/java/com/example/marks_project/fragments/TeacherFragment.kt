package com.example.marks_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.marks_project.R
import com.example.marks_project.adapters.MyPagerAdapter
import com.example.marks_project.database.SchoolSystemDatabase
import com.example.marks_project.database.entity.Group
import com.example.marks_project.database.entity.TeacherSubject
import com.example.marks_project.database.entity.User
import com.example.marks_project.databinding.FragmentTeacherBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator


class TeacherFragment : Fragment() {
    private lateinit var binding: FragmentTeacherBinding
    private lateinit var myDb: SchoolSystemDatabase
    private lateinit var allTeacherSubjects: List<TeacherSubject>
    lateinit var user: User
    private val teacherGroups = mutableListOf<Group>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeacherBinding.inflate(inflater, container, false)

        myDb = SchoolSystemDatabase.getInstance(requireContext())
        user = arguments?.getSerializable("user") as User

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        bottomSheetDialog.setContentView(view)

        binding.logout.setOnClickListener {
            findNavController().navigate(R.id.action_teacherFragment_to_signInFragment)
        }

        val classSpinner = view.findViewById<Spinner>(R.id.class_spinner)
        val classes = myDb.groupDao().getAllGroups()
        val classAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, classes.map { it.name })
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classSpinner.adapter = classAdapter

        val subjectSpinner = view.findViewById<Spinner>(R.id.subject_spinner)
        val subjects = myDb.subjectDao().getAllSubjects()
        val subjectAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subjects.map { it.name })
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subjectSpinner.adapter = subjectAdapter

        allTeacherSubjects = myDb.teacherSubjectDao().getTeacherSubjectsByUserId(userId = user.userId)

        for (i in allTeacherSubjects) {
            val group: Group = myDb.groupDao().getGroupById(i.groupId)!!

            teacherGroups.add(group)
        }

        var selectedSubjectName = subjectSpinner.selectedItem
        var selectedSubject = subjects.find { it.name == selectedSubjectName }


        val adapter = MyPagerAdapter(requireActivity(), teacherGroups, teacherId = user.userId, subjectId = myDb.subjectDao().getSubjectById(myDb.teacherSubjectDao().getSubjectIdByUserId(user.userId))?.subjectId ?: 0)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab,position -> }.attach()

        updateTeacherSubjects()

        binding.plus.setOnClickListener {
            bottomSheetDialog.show()
        }

        val btnOk = view.findViewById<Button>(R.id.btn_ok)
        btnOk.setOnClickListener {
            var selectedSubjectName = subjectSpinner.selectedItem
            var selectedSubject = subjects.find { it.name == selectedSubjectName }

            var selectedClassName = classSpinner.selectedItem
            var selectedClass = classes.find { it.name == selectedClassName }

            var teacherSubject = TeacherSubject(
                id = 0,
                userId = user.userId,
                subjectId = selectedSubject!!.subjectId,
                groupId = selectedClass!!.groupId,
            )

            Log.d("SIZE", allTeacherSubjects.size.toString())
            for (teacherS in allTeacherSubjects) {
                Log.d("NEW", "GROUP ${teacherSubject.groupId}, SUBJECT ID : ${teacherSubject.subjectId}")
                if (teacherS.groupId == teacherSubject.groupId && teacherS.subjectId == teacherSubject.subjectId) {
                    Toast.makeText(requireContext(), "This teacher subject is available", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            myDb.teacherSubjectDao().insertTeacherSubject(teacherSubject)
            updateTeacherSubjects()

            bottomSheetDialog.dismiss()
        }

        return binding.root
    }

    private fun updateTeacherSubjects() {
        allTeacherSubjects = myDb.teacherSubjectDao().getTeacherSubjectsByUserId(userId = user.userId)

        binding.tabLayout.removeAllTabs()

        for (i in allTeacherSubjects) {
            var tab = binding.tabLayout.newTab()
            tab.text = "${myDb.subjectDao().getSubjectById(i.subjectId)!!.name} - ${myDb.groupDao().getGroupById(i.groupId)!!.name}"
            binding.tabLayout.addTab(tab)
        }
    }
}