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
import com.certified.notekeeper.model.Course;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Samson.
 */

public class CourseRecyclerAdapter extends ListAdapter<Course, CourseRecyclerAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Course> DIFF_CALLBACK = new DiffUtil.ItemCallback<Course>() {
        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getCourseTitle().equals(newItem.getCourseTitle()) &&
                    oldItem.getCourseId().equals(newItem.getCourseId());
        }
    };

    public CourseRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = getItem(position);
        holder.mTextCourse.setText(course.getCourseTitle());
        holder.mCurrentPosition = position;
        holder.itemView.setOnClickListener(v -> {
            Snackbar.make(v, "You clicked: " + course.getCourseTitle(), Snackbar.LENGTH_LONG).show();
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextCourse;
        public int mCurrentPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextCourse = itemView.findViewById(R.id.text_course);
        }
    }
}