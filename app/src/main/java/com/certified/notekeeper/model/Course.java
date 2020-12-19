package com.certified.notekeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.certified.notekeeper.ModuleInfo;

import java.util.List;

@Entity(tableName = "course_info")
public class Course {

//    Columns
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "course_id")
    private String courseId;

    @NonNull
    @ColumnInfo(name = "course_title")
    private String courseTitle;

    @Ignore
    @ColumnInfo(name = "course_title")
    private List<ModuleInfo> modules;

    public Course(String courseId, String courseTitle) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }
}
