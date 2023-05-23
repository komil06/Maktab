package com.example.marks_project

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marks_project.database.SchoolSystemDatabase
import com.example.marks_project.database.entity.Group
import com.example.marks_project.database.entity.StudentSubject
import com.example.marks_project.database.entity.Subject
import com.example.marks_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val dataAdded = sharedPreferences.getBoolean("data_added", false)

        if (!dataAdded) {
            val myDb = SchoolSystemDatabase.getInstance(this)
            val classNames = arrayOf(
                "5-01", "5-02", "5-03", "5-04", "5-05",
                "6-01", "6-02", "6-03", "6-04", "6-05",
                "7-01", "7-02", "7-03", "7-04", "7-05",
                "8-01", "8-02", "8-03", "8-04", "8-05",
                "9-01", "9-02", "9-03", "9-04", "9-05",
                "10-01", "10-02", "10-03", "10-04", "10-05",
                "11-01", "11-02", "11-03", "11-04", "11-05",
            )

            for (className in classNames) {
                val group = Group(sinf = className.substringBefore("-"), name = className, groupId = 0)
                myDb.groupDao().insertGroup(group)
            }

            val subjects = arrayOf("Matematika", "Informatika", "Fizika", "English")

            for (subjectName in subjects) {
                val subject = Subject(subjectId = 0, name = subjectName)
                myDb.subjectDao().insertSubject(subject)
            }

            // Mark data as added
            val editor = sharedPreferences.edit()
            editor.putBoolean("data_added", true)
            editor.apply()
        }
    }
}