package com.certified.notekeeper;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.certified.notekeeper.model.Course;
import com.certified.notekeeper.model.Note;
import com.certified.notekeeper.room.NoteKeeperDao;
import com.certified.notekeeper.room.NoteKeeperDatabase;

import java.util.List;

public class NoteRepository {

    private NoteKeeperDao noteKeeperDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Course>> allCourses;
    private LiveData<String> courseId;

    public NoteRepository(Application application) {
        NoteKeeperDatabase database = NoteKeeperDatabase.getInstance(application);
        noteKeeperDao = database.noteKeeperDao();
        allNotes = noteKeeperDao.getAllNotes();
        allCourses = noteKeeperDao.getAllCourses();
    }

    public void insertNote(Note note) {
        NoteKeeperDatabase.databaseWriteExecutor.execute(() -> noteKeeperDao.insertNote(note));
    }

    public void insertCourse(Course course) {
        NoteKeeperDatabase.databaseWriteExecutor.execute(() -> noteKeeperDao.insertCourse(course));
    }

    public void updateNote(Note note) {
        NoteKeeperDatabase.databaseWriteExecutor.execute(() -> noteKeeperDao.updateNote(note));
    }

    public void updateCourse(Course course) {
        NoteKeeperDatabase.databaseWriteExecutor.execute(() -> noteKeeperDao.updateCourse(course));
    }

    public void deleteNote(Note note) {
        NoteKeeperDatabase.databaseWriteExecutor.execute(() -> noteKeeperDao.deleteNote(note));
    }

    public void deleteCourse(Course course) {
        NoteKeeperDatabase.databaseWriteExecutor.execute(() -> noteKeeperDao.deleteCourse(course));
    }

    public void deleteAllNotes() {
        NoteKeeperDatabase.databaseWriteExecutor.execute(() -> noteKeeperDao.deleteAllNotes());
    }

    public void deleteAllCourses() {
        NoteKeeperDatabase.databaseWriteExecutor.execute(() -> noteKeeperDao.deleteAllCourses());
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public String getCourseId(String courseTitle) {
        NoteKeeperDatabase.databaseWriteExecutor.submit(() -> noteKeeperDao.getCourseId(courseTitle));
//        return noteKeeperDao.getCourseIdAt(courseTitle);
        return "";
    }
}