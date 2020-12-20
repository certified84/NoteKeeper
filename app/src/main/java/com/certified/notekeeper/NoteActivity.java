package com.certified.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.certified.notekeeper.model.Course;
import com.certified.notekeeper.model.Note;
import com.certified.notekeeper.room.NoteKeeperViewModel;
import com.certified.notekeeper.util.Extras;

import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

public class NoteActivity extends AppCompatActivity {

    public static final int ID_NOT_SET = -1;
    private final String TAG = getClass().getSimpleName();
    private String mOriginalNoteCourseId;
    private String mOriginalNoteTitle;
//    private String mCourseId;
    private String mOriginalNoteText;
    private int mNoteId;
    private boolean mIsCancelling;
    private int spinnerSelection;

    private Spinner mSpinnerCourses;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;

    private NoteKeeperViewModel viewModel;
    private Intent intent;
    private ArrayList<String> courseList;
    private ArrayAdapter<String> mAdapterCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel = new NoteKeeperViewModel(getApplication());

        mTextNoteTitle = findViewById(R.id.text_note_title);
        mTextNoteText = findViewById(R.id.text_note_text);
        mSpinnerCourses = findViewById(R.id.spinner_courses);

        courseList = new ArrayList<>();

        viewModel.getAllCourses().observe(this, courses -> {
            for (Course course : courses) {
                courseList.add(course.getCourseTitle());
            }
            mAdapterCourses.notifyDataSetChanged();
        });

        mAdapterCourses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseList);
        mAdapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCourses.setAdapter(mAdapterCourses);

        intent = getIntent();
        if (intent.hasExtra(Extras.EXTRA_ID)) {
            mOriginalNoteTitle = intent.getStringExtra(Extras.EXTRA_NOTE_TITLE);
            mOriginalNoteText = intent.getStringExtra(Extras.EXTRA_NOTE_TEXT);
            mOriginalNoteCourseId = intent.getStringExtra(Extras.EXTRA_COURSE_ID);
            mNoteId = intent.getIntExtra(Extras.EXTRA_ID, ID_NOT_SET);

            switch (mOriginalNoteCourseId) {
                case "java_lang":
                    spinnerSelection = 0;
                    break;
                case "android_intents":
                    spinnerSelection = 1;
                    break;
                case "java_core":
                    spinnerSelection = 2;
                    break;
                case "android_async":
                    spinnerSelection = 3;
                    break;
            }

        } else
            setTitle("Add Note");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (intent.hasExtra(Extras.EXTRA_ID)) {
            mSpinnerCourses.setSelection(spinnerSelection);
            mTextNoteTitle.setText(mOriginalNoteTitle);
            mTextNoteText.setText(mOriginalNoteText);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsCancelling)
            return;
        saveNote();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Extras.EXTRA_COURSE_ID, mOriginalNoteCourseId);
        outState.putString(Extras.EXTRA_NOTE_TITLE, mOriginalNoteTitle);
        outState.putString(Extras.EXTRA_NOTE_TEXT, mOriginalNoteText);
    }

    private void saveNote() {

        String noteTitle = mTextNoteTitle.getText().toString().trim();
        String noteText = mTextNoteText.getText().toString().trim();
        String courseTitle = mSpinnerCourses.getSelectedItem().toString();
        String courseId = viewModel.getCourseId(courseTitle);

        if (isEmpty(noteTitle)) {
            Log.i(TAG, "saveNote: Note can't be empty");
            Toast.makeText(this, "Empty note not saved", Toast.LENGTH_SHORT).show();
            return;
        }

        if (intent.hasExtra(Extras.EXTRA_ID)) {
            if (!noteTitle.equals(mOriginalNoteTitle) || !noteText.equals(mOriginalNoteText)
                    || !courseId.equals(mOriginalNoteCourseId)) {

                Log.i(TAG, "saveNote: Updating note");
                Note note = new Note(courseId, noteTitle, noteText);
                note.setId(mNoteId);
                viewModel.updateNote(note);
            }
        } else {
            Log.i(TAG, "saveNote: saving new note");
            Note note = new Note(courseId, noteTitle, noteText);
            viewModel.insertNote(note);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail) {
            sendEmail();
            return true;
        } else if (id == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
        } else if (id == R.id.action_next) {
            moveNext();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_next);
//        int lastNoteIndex = DataManager.getInstance().getNotes().size() - 1;
        int lastNoteIndex = 0;
        item.setEnabled(mNoteId < lastNoteIndex);
        return super.onPrepareOptionsMenu(menu);
    }

    private void moveNext() {
//        saveNote();

        ++mNoteId;
//        mNote = DataManager.getInstance().getNotes().get(mNoteId);

        if (!mTextNoteTitle.getText().toString().equals(mOriginalNoteTitle)
                || !mTextNoteText.getText().toString().equals(mOriginalNoteText)) {

            Note note = new Note(mSpinnerCourses.getSelectedItem().toString(),
                    mTextNoteTitle.getText().toString(),
                    mTextNoteText.getText().toString());

            viewModel.updateNote(note);
        }
//        saveOriginalNoteValues();
//        displayNote();
        invalidateOptionsMenu();
    }

    private void sendEmail() {
        String course = mSpinnerCourses.getSelectedItem().toString();
        String subject = mTextNoteTitle.getText().toString();
        String text = "Checkout what I learned in the Pluralsight course \"" +
                course + "\"\n" + mTextNoteText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }
}