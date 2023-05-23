package com.example.marks_project.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mark",
    foreignKeys = [
        ForeignKey(
            entity = StudentSubject::class,
            parentColumns = ["id"],
            childColumns = ["studentSubjectId"]
        ),
        ForeignKey(
            entity = TeacherSubject::class,
            parentColumns = ["id"],
            childColumns = ["teacherSubjectId"]
        ),
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["subjectId"],
            childColumns = ["subjectId"]
        )
    ],
    indices = [
        Index("studentSubjectId"),
        Index("teacherSubjectId"),
        Index("subjectId")
    ]
)
data class Mark(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val studentSubjectId: Int,
    val teacherSubjectId: Int,
    val subjectId: Int,
    val mark: Int
)
