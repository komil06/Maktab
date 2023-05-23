package com.example.marks_project.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.marks_project.database.dao.GroupDao
import com.example.marks_project.database.dao.MarkDao
import com.example.marks_project.database.dao.StudentSubjectDao
import com.example.marks_project.database.dao.SubjectDao
import com.example.marks_project.database.dao.TeacherSubjectDao
import com.example.marks_project.database.dao.UserDao
import com.example.marks_project.database.entity.Group
import com.example.marks_project.database.entity.Mark
import com.example.marks_project.database.entity.StudentSubject
import com.example.marks_project.database.entity.Subject
import com.example.marks_project.database.entity.TeacherSubject
import com.example.marks_project.database.entity.User

@Database(
    entities = [
        User::class,
        TeacherSubject::class,
        StudentSubject::class,
        Subject::class,
        Group::class,
        Mark::class
    ],
    version = 3,
    exportSchema = false
)
abstract class SchoolSystemDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun teacherSubjectDao(): TeacherSubjectDao
    abstract fun studentSubjectDao(): StudentSubjectDao
    abstract fun subjectDao(): SubjectDao
    abstract fun groupDao(): GroupDao
    abstract fun markDao(): MarkDao


    class AddGroupIdToTeacherSubjectMigration : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE mark ADD COLUMN subjectId INTEGER NOT NULL DEFAULT 0")
        }
    }

    companion object {
        private const val DATABASE_NAME = "school_system_database"

        @Volatile
        private var INSTANCE: SchoolSystemDatabase? = null

        fun getInstance(context: Context): SchoolSystemDatabase  {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, SchoolSystemDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(AddGroupIdToTeacherSubjectMigration())
                    .build()
            }

            return INSTANCE!!
        }
    }
}
