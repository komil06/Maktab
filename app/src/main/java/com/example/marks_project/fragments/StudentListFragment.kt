package com.example.marks_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marks_project.adapters.MyRecycleViewAdapter
import com.example.marks_project.database.SchoolSystemDatabase
import com.example.marks_project.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {

    private lateinit var studentListAdapter: MyRecycleViewAdapter
    private lateinit var myDb: SchoolSystemDatabase
    private var groupId: Int = 0
    private var teacherId: Int = 0
    private var subjectId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            groupId = it.getInt("groupId")
            teacherId = it.getInt("teacherId")
            subjectId = it.getInt("subjectId")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStudentListBinding.inflate(inflater, container, false)

        myDb = SchoolSystemDatabase.getInstance(requireContext())

        studentListAdapter = MyRecycleViewAdapter(
            students = myDb.studentSubjectDao().getStudentsByGroupId(groupId),
            context = requireContext(),
            myDb = myDb,
            subjectId = subjectId,
            teacherId = teacherId
        )
        binding.rvStudentList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudentList.adapter = studentListAdapter

        return binding.root
    }

    companion object {
        fun newInstance(groupId: Int, subjectId: Int, teacherId: Int): StudentListFragment {
            val args = Bundle()
            args.putInt("groupId", groupId)
            args.putInt("subjectId", subjectId)
            args.putInt("teacherId", teacherId)
            val fragment = StudentListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
