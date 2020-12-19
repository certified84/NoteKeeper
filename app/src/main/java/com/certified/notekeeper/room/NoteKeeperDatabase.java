package com.certified.notekeeper.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.certified.notekeeper.model.Course;
import com.certified.notekeeper.model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class, Course.class}, version = 1, exportSchema = false)
public abstract class NoteKeeperDatabase extends RoomDatabase {

    private static final String TAG = "NoteKeeperDatabase";
    private static final int NUMBER_OF_THREADS = 8;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static NoteKeeperDatabase instance;

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            onOpen
            Note note1 = new Note("android_intents", "Dynamic intent resolution", "Wow, intents allow components to be resolved at runtime");
            Note note2 = new Note("java_lang", "Parameters", "Leverage variable-length parameter lists?");
            Note note3 = new Note("android_intents", "Delegating intents", "PendingIntents are powerful; they delegate much more than just a component invocation");
            Note note4 = new Note("java_core", "Compiler options", "The -jar option isn't compatible with with the -cp option");
            Note note5 = new Note("java_core", "Serialization", "Remember to include SerialVersionUID to assure version compatibility");
            Note note6 = new Note("android_async", "Service default threads", "Did you know that by default an Android Service will tie up the UI thread?");
            Note note7 = new Note("java_lang", "Anonymous classes", "Anonymous classes simplify implementing one-use types");
            Note note8 = new Note("android_async", "Long running operations", "Foreground Services can be tied to a notification icon");

            Course course1 = new Course("java_lang", "Java Fundamentals: The Java Language");
            Course course2 = new Course("android_intents", "Android Programming with Intents");
            Course course3 = new Course("java_core", "Java Fundamentals: The Core Platform");
            Course course4 = new Course("android_async", "Android Async Programming and Services");

            databaseWriteExecutor.execute(() -> {
                NoteKeeperDao noteKeeperDao = instance.noteKeeperDao();

                noteKeeperDao.deleteAllNotes();
                noteKeeperDao.insertNote(note1);
                noteKeeperDao.insertNote(note2);
                noteKeeperDao.insertNote(note3);
                noteKeeperDao.insertNote(note4);
                noteKeeperDao.insertNote(note5);
                noteKeeperDao.insertNote(note6);
                noteKeeperDao.insertNote(note7);
                noteKeeperDao.insertNote(note8);

                noteKeeperDao.deleteAllCourses();
                noteKeeperDao.insertCourse(course1);
                noteKeeperDao.insertCourse(course2);
                noteKeeperDao.insertCourse(course3);
                noteKeeperDao.insertCourse(course4);
            });
        }
    };

    public static synchronized NoteKeeperDatabase getInstance(Context context) {
        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteKeeperDatabase.class, "NoteKeeper.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract NoteKeeperDao noteKeeperDao();
}
