package com.example.marks_project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.marks_project.R
import com.example.marks_project.database.SchoolSystemDatabase
import com.example.marks_project.database.entity.StudentSubject
import com.example.marks_project.database.entity.User
import com.example.marks_project.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val user = arguments?.getSerializable("user") as User

        var myDb = SchoolSystemDatabase.getInstance(requireContext())

        if (user.role == "Student") {
            var bundle = bundleOf("studentId" to myDb.studentSubjectDao().getStudentSubjectByUserId(user.userId)!!.id)
            findNavController().navigate(R.id.action_homeFragment_to_studentFragment2, bundle)
        }
        else {
            var bundle = bundleOf("user" to user)
            findNavController().navigate(R.id.action_homeFragment_to_teacherFragment, bundle)
        }



        return binding.root
    }
}