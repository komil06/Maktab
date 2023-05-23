package com.example.marks_project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val subjectId: Int,
    val name: String
)
