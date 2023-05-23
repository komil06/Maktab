package com.example.marks_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marks_project.R
import com.example.marks_project.adapters.MyRecycleViewAdapter
import com.example.marks_project.adapters.StudentMarksAdapter
import com.example.marks_project.database.SchoolSystemDatabase
import com.example.marks_project.databinding.FragmentStudentBinding

class StudentFragment : Fragment() {
    private lateinit var myDb: SchoolSystemDatabase
    private lateinit var studentMarksAdapter: StudentMarksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStudentBinding.inflate(inflater, container, false)

        myDb = SchoolSystemDatabase.getInstance(requireContext())

        var studentId = arguments?.getInt("studentId")

        studentMarksAdapter = StudentMarksAdapter(
            subjects = myDb.subjectDao().getAllSubjects(),
            context = requireContext(),
            myDb = myDb,
            studentId = studentId!!
        )
        binding.rvStudentSubjectMarks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudentSubjectMarks.adapter = studentMarksAdapter

        binding.logout.setOnClickListener {
            findNavController().navigate(R.id.action_studentFragment_to_signInFragment)
        }

        return binding.root
    }
}