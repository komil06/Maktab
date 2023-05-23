package com.example.marks_project.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "student_subject",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"]
        ),
        ForeignKey(
            entity = Group::class,
            parentColumns = ["groupId"],
            childColumns = ["groupId"]
        )
    ],
    indices = [
        Index("userId"),
        Index("groupId")
    ]
)
data class StudentSubject(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: Int,
    val groupId: Int
)
