package com.example.marks_project.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.marks_project.database.SchoolSystemDatabase
import com.example.marks_project.database.entity.Mark
import com.example.marks_project.database.entity.StudentSubject
import com.example.marks_project.databinding.ItemStudentBinding


class MyRecycleViewAdapter(
    private val students: List<StudentSubject>,
    val context: Context,
    private var myDb: SchoolSystemDatabase,
    private var teacherId: Int,
    var subjectId: Int,
) : RecyclerView.Adapter<MyRecycleViewAdapter.MyHolder>() {

    class MyHolder(itemView: ItemStudentBinding) : RecyclerView.ViewHolder(itemView.root) {
        val name = itemView.studentName
        val mark = itemView.studentMark
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val item = students[position]

        holder.name.text = myDb.userDao().getUserById(item.userId)!!.fullName
        val mark: Int? = myDb.markDao().getStudentMarkByTeacherSubjectId(studentSubjectId = item.id, teacherSubjectId = teacherId)?.mark
        if (mark != null) {
            holder.mark.setText(mark.toString())
        } else {
            holder.mark.setText(" ")
        }

        holder.mark.addTextChangedListener {
            myDb.runInTransaction {
                val studentSubject = myDb.studentSubjectDao().getStudentSubjectById(item.id)
                val teacherSubject = myDb.teacherSubjectDao().getTeacherSubjectById(teacherId)

                if (studentSubject != null && teacherSubject != null) {
                    if (mark == null) {
                        Log.d("KIRDI", "HA")
                        var newMark = holder.mark.text.toString().trim().toIntOrNull() ?: -1
                        if (newMark != -1) {
                            myDb.markDao().insertMark(
                                Mark(
                                    id = 0,
                                    studentSubjectId = studentSubject.id,
                                    teacherSubjectId = teacherSubject.id,
                                    subjectId = subjectId,
                                    mark = newMark
                                )
                            )
                        }
                    } else {
                        Log.d("KIRDI", "YOQ")
                        var newMark = holder.mark.text.toString().trim().toIntOrNull() ?: -1
                        if (newMark != -1) {
                            val existingMark = myDb.markDao().getStudentMarkByTeacherSubjectId(
                                studentSubjectId = studentSubject.id,
                                teacherSubjectId = teacherSubject.id
                            )
                            if (existingMark != null) {
                                myDb.markDao().updateMarkValueById(id = existingMark.id, mark = newMark)
                            }
                        }
                    }
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return students.size
    }
}