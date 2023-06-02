package rlin.com.rlintodolist.ui.main;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rlin.com.rlintodolist.R;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    public final static String TAG = "TodoListAdapter";
    private final List<Task> mValues;
    final OnAdapterItemInteraction mListener;

    public TaskRecyclerViewAdapter(List<Task> items, OnAdapterItemInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_linear, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtTitle.setText(mValues.get(position).getTitle());
        holder.txtDueDate.setText(mValues.get(position).getDueDate());

        if (TextUtils.isEmpty(mValues.get(position).getDescription())) {
            holder.txtDescription.setText("{no description....}");
        } else {
            holder.txtDescription.setText(mValues.get(position).getDescription());
        }

        int colorResId;

        switch (mValues.get(position).getPriority()) {
            case Task.HIGH:
                colorResId = android.R.color.holo_red_light; // Example: Set to red color
                break;
            case Task.MEDIUM:
                colorResId = android.R.color.holo_orange_light; // Example: Set to orange color
                break;
            case Task.LOW:
                colorResId = android.R.color.holo_green_light; // Example: Set to green color
                break;
            default:
                colorResId = android.R.color.transparent; // Set default color or handle other cases
                break;
        }
        holder.priorityLayout.setBackgroundResource(colorResId);

        final Task task = mValues.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onItemSelected(task);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) {
                    mListener.onItemDeleteClicked(task);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(Task task) {
        Log.d(TAG, "Add" + task.toString());

        mValues.add(task);
        notifyItemInserted(mValues.size() - 1);
    }

    public void remove(Task task) {
        int pos = mValues.indexOf(task);
        mValues.remove(pos);
        notifyItemRemoved(pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtTitle;
        public final TextView txtDescription;
        public final TextView txtDueDate;

        public final Button deleteButton;

        public final LinearLayout priorityLayout;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            txtTitle = view.findViewById(R.id.title);
            txtDescription = view.findViewById(R.id.description);
            txtDueDate = view.findViewById(R.id.dueDate);
            priorityLayout = view.findViewById(R.id.priorityLayout);
            deleteButton = view.findViewById(R.id.todo_deleteButton);
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + txtTitle.getText() + "'";
        }
    }

    public interface OnAdapterItemInteraction {
        void onItemSelected(Task task);
        void onItemDeleteClicked(Task task);
    }
}
