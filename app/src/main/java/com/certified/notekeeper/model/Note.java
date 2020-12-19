package com.certified.notekeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_info")
public class Note {

    //    Columns
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "note_title")
    private String noteTitle;

    @ColumnInfo(name = "note_text")
    private String noteText;

    @NonNull
    @ColumnInfo(name = "course_id")
    private String courseId;

    public Note(String courseId, String noteTitle, String noteText) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getCourseId() {
        return courseId;
    }
}
