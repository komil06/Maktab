package com.example.marks_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.marks_project.R
import com.example.marks_project.database.SchoolSystemDatabase
import com.example.marks_project.database.entity.StudentSubject
import com.example.marks_project.database.entity.User
import com.example.marks_project.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var users: List<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)

        val myDb = SchoolSystemDatabase.getInstance(requireContext())

        var users = myDb.userDao().getAllUsers()

        val classes = myDb.groupDao().getAllGroups()
        val classAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, classes.map { it.name })
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.studentGroup.adapter = classAdapter

        binding.spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedRole = parent.getItemAtPosition(position).toString()
                if (selectedRole == "Student") {
                    binding.studentGroup.visibility = View.VISIBLE
                } else {
                    binding.studentGroup.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val login = binding.etLogin.text.toString()
            var role = binding.spinnerRole.selectedItem as String

            if (name.isNotEmpty() && password.isNotEmpty() && login.isNotEmpty()) {
                val newUser = User(userId = 0, fullName = name, password = password, login = login, role = role)

                for (i in users) {
                    if (i.login == newUser.login) {
                        Toast.makeText(requireContext(), "This login is already taken", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                if (newUser.role == "Student") {
                    val selectedClassName = binding.studentGroup.selectedItem
                    val selectedClass = classes.find { it.name == selectedClassName }

                    myDb.runInTransaction {
                        val insertedUserId = myDb.userDao().insertUser(newUser)

                        val studentSubject = StudentSubject(id = 0, userId = insertedUserId.toInt(), groupId = selectedClass!!.groupId)
                        myDb.studentSubjectDao().insertStudentSubject(studentSubject)

                        Toast.makeText(requireContext(), "Successfully registered!", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    myDb.userDao().insertUser(newUser)
                }
                Toast.makeText(requireContext(), "Successfully registered!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Fill all gaps bro!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signInInSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        return binding.root
    }
}