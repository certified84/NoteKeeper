package com.certified.notekeeper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.certified.notekeeper.R;
import com.certified.notekeeper.model.Note;

/**
 * Created by Samson.
 */

public class NoteRecyclerAdapter extends ListAdapter<Note, NoteRecyclerAdapter.ViewHolder> {

    private static final String TAG = "NoteRecyclerAdapter";
    private OnNoteClickedListener listener;

    public NoteRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNoteTitle().equals(newItem.getNoteTitle()) &&
                    oldItem.getNoteText().equals(newItem.getNoteText()) &&
                    oldItem.getCourseId().equals(newItem.getCourseId());
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        mCursor.moveToPosition(position);
//        String course = mCursor.getString(mCoursePos);
//        String noteTitle = mCursor.getString(mNoteTitlePos);
//        int id = mCursor.getInt(mIdPos);

        Note currentNote = getItem(position);
        holder.mTextCourse.setText(currentNote.getNoteText());
        holder.mTextTitle.setText(currentNote.getNoteTitle());
        holder.mId = currentNote.getCourseId();
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

//    @Override
//    public int getItemCount() {
//        return mCursor == null ? 0 : mCursor.getCount();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextCourse;
        public final TextView mTextTitle;
        public String mId;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextCourse = itemView.findViewById(R.id.text_course);
            mTextTitle = itemView.findViewById(R.id.text_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                    }
                    listener.onNoteClick(getItem(position));
//                    Intent intent = new Intent(mContext, NoteActivity.class);
//                    intent.putExtra(NoteActivity.NOTE_ID, mId);
//                    mContext.startActivity(intent);
                }
            });
        }
    }

    public interface OnNoteClickedListener {
        void onNoteClick(Note note);
    }

    public void setOnNoteClickedListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }
}








