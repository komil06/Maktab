package com.example.marks_project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "guruh")
data class Group(
    @PrimaryKey(autoGenerate = true)
    val groupId: Int,
    val name: String,
    val sinf: String
) : Serializable
