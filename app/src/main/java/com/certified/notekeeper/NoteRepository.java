package com.certified.notekeeper;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.certified.notekeeper.model.Course;
import com.certified.notekeeper.model.Note;
import com.certified.notekeeper.room.NoteKeeperDao;
import com.certified.notekeeper.room.NoteKeeperDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        executor.execute(() -> noteKeeperDao.insertNote(note));
    }

    public void insertCourse(Course course) {
        executor.execute(() -> noteKeeperDao.insertCourse(course));
    }

    public void updateNote(Note note) {
        executor.execute(() -> noteKeeperDao.updateNote(note));
    }

    public void updateCourse(Course course) {
        executor.execute(() -> noteKeeperDao.updateCourse(course));
    }

    public void deleteNote(Note note) {
        executor.execute(() -> noteKeeperDao.deleteNote(note));
    }

    public void deleteCourse(Course course) {
        executor.execute(() -> noteKeeperDao.deleteCourse(course));
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
        try {
            return executor.submit(() -> noteKeeperDao.getCourseId(courseTitle)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static final ExecutorService executor = Executors.newSingleThreadExecutor();
}