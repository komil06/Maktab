package com.example.marks_project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val fullName: String,
    val login: String,
    val password: String,
    val role: String
) : Serializable
