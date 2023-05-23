package com.example.marks_project.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marks_project.database.entity.Group
import com.example.marks_project.database.entity.Mark
import com.example.marks_project.database.entity.StudentSubject
import com.example.marks_project.database.entity.Subject
import com.example.marks_project.database.entity.TeacherSubject
import com.example.marks_project.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user WHERE login = :login AND password = :password")
    fun login(login: String, password: String): User?

    @Insert
    fun insertUser(user: User) : Long

    @Delete
    fun deleteUser(user: User)
}

@Dao
interface TeacherSubjectDao {
    @Query("SELECT * FROM teacher_subject")
    fun getAllTeacherSubjects(): List<TeacherSubject>

    @Query("SELECT * FROM teacher_subject WHERE id = :id")
    fun getTeacherSubjectById(id: Int): TeacherSubject?

    @Query("SELECT * FROM teacher_subject WHERE userId = :userId")
    fun getTeacherSubjectsByUserId(userId: Int): List<TeacherSubject>

    @Insert
    fun insertTeacherSubject(teacherSubject: TeacherSubject)

    @Delete
    fun deleteTeacherSubject(teacherSubject: TeacherSubject)

    @Query("SELECT subjectId FROM teacher_subject WHERE userId = :userId")
    fun getSubjectIdByUserId(userId: Int): Int
}

@Dao
interface StudentSubjectDao {
    @Query("SELECT * FROM student_subject WHERE id = :id")
    fun getStudentSubjectById(id: Int): StudentSubject?

    @Query("SELECT * FROM student_subject WHERE userId = :userId")
    fun getStudentSubjectByUserId(userId: Int): StudentSubject?

    @Query("SELECT * FROM student_subject WHERE groupId = :groupId")
    fun getStudentsByGroupId(groupId: Int): List<StudentSubject>

    @Insert
    fun insertStudentSubject(studentSubject: StudentSubject)

    @Delete
    fun deleteStudentSubject(studentSubject: StudentSubject)
}

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subject")
    fun getAllSubjects(): List<Subject>

    @Query("SELECT * FROM subject WHERE subjectId = :subjectId")
    fun getSubjectById(subjectId: Int): Subject?

    @Insert
    fun insertSubject(subject: Subject)

    @Delete
    fun deleteSubject(subject: Subject)
}

@Dao
interface GroupDao {
    @Query("SELECT * FROM guruh")
    fun getAllGroups(): List<Group>

    @Query("SELECT * FROM guruh WHERE groupId = :groupId")
    fun getGroupById(groupId: Int): Group?

    @Insert
    fun insertGroup(group: Group)

    @Delete
    fun deleteGroup(group: Group)
}

@Dao
interface MarkDao {
    @Query("SELECT * FROM mark WHERE id = :id")
    fun getMarkById(id: Int): Mark?

    @Insert
    fun insertMark(mark: Mark)

    @Query("UPDATE mark SET mark = :mark WHERE id = :id")
    fun updateMarkValueById(id: Int, mark: Int)

    @Delete
    fun deleteMark(mark: Mark)

    @Query("SELECT * FROM mark WHERE teacherSubjectId = :teacherSubjectId " +
            "AND studentSubjectId = :studentSubjectId")
    fun getStudentMarkByTeacherSubjectId(teacherSubjectId: Int, studentSubjectId: Int): Mark?

    @Query("SELECT * FROM mark WHERE studentSubjectId = :studentSubjectId")
    fun getStudentMarkByStudentSubjectId(studentSubjectId: Int): Mark?

    @Query("SELECT * FROM mark WHERE studentSubjectId = :studentSubjectId")
    fun getMarksByStudentSubjectId(studentSubjectId: Int): List<Mark>

    @Query("SELECT * FROM mark WHERE studentSubjectId = :studentSubjectId AND subjectId = :subjectId")
    fun getMarkByStudentSubjectIdAndSubjectId(studentSubjectId: Int, subjectId: Int): Mark?

    @Query("SELECT * FROM mark WHERE teacherSubjectId = :teacherSubjectId")
    fun getMarksByTeacherSubjectId(teacherSubjectId: Int): List<Mark>
}
