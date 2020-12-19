package com.certified.notekeeper.room;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.certified.notekeeper.model.Course;
import com.certified.notekeeper.model.Note;

import java.util.List;

@Dao
public interface NoteKeeperDao {

    @Insert
    void insertNote(Note note);

    @Insert
    void insertCourse(Course course);

    @Update
    void updateNote(Note note);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteNote(Note note);

    @Delete
    void deleteCourse(Course course);

    @Query("DELETE FROM note_info")
    void deleteAllNotes();


    @Query("DELETE FROM course_info")
    void deleteAllCourses();

    @Query("SELECT * FROM note_info ORDER BY note_title ASC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM course_info")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT course_id FROM course_info WHERE course_title = :courseTitle")
    String getCourseId(String courseTitle);
}