package com.certified.notekeeper.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.certified.notekeeper.NoteRepository;
import com.certified.notekeeper.model.Course;
import com.certified.notekeeper.model.Note;

import java.util.List;

public class NoteKeeperViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Course>> allCourses;

    public NoteKeeperViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        allCourses = repository.getAllCourses();
    }

    public void insertNote(Note note) {
        repository.insertNote(note);
    }

    public void insertCourse(Course course) {
        repository.insertCourse(course);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public void updateCourse(Course course) {
        repository.updateCourse(course);
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }

    public void deleteCourse(Course course) {
        repository.deleteCourse(course);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public String getAllCourseIdAt(String courseTitle) {
        return repository.getCourseId(courseTitle);
    }
}
