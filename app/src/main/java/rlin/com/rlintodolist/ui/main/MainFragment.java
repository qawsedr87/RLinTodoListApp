package rlin.com.rlintodolist.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import rlin.com.rlintodolist.R;

public class MainFragment extends Fragment implements TaskRecyclerViewAdapter.OnAdapterItemInteraction {

    public final static String TAG = "TodoList";

    TaskRecyclerViewAdapter adapter;
    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // avoid obscured when keyboard popup
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: database instant

        // Instantiate the recyclerView
        RecyclerView recyclerView = getActivity().findViewById(R.id.todo_tasks);

        // Instantiate the layoutManager and set it into the recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Instantiate an TaskRecyclerViewAdapter
        // pass the data from the viewModel and the object
        Log.d(TAG, "Total task size: " + mViewModel.getTasks().size());
        adapter = new TaskRecyclerViewAdapter(mViewModel.getTasks(), this);

        // Set the adapter into recyclerView
        recyclerView.setAdapter(adapter);

        // Save button
        Button saveButton = getActivity().findViewById(R.id.todo_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });
    }


    private void onSave() {
        EditText taskTitle = getActivity().findViewById(R.id.todo_title);
        String title = taskTitle.getText().toString();

        EditText taskDescription = getActivity().findViewById(R.id.todo_description);
        String description = taskDescription.getText().toString();

        EditText taskDueDate = getActivity().findViewById(R.id.todo_dueDate);
        String dueDate = taskDueDate.getText().toString();

        // Check title and dueDate are not empty
        if (TextUtils.isEmpty(title)) {
            showMissingInfoToast("Title");
        } else if (TextUtils.isEmpty(dueDate)) {
            showMissingInfoToast("Due Date");
        } else {
            Spinner taskPriority = getActivity().findViewById(R.id.todo_priority);
            String priority = taskPriority.getSelectedItem().toString();

            // Add the object at the end of the array kept in the viewModel
            Task saveTask = mViewModel.addTask(title, description, dueDate, priority);

            adapter.notifyDataSetChanged();

            Log.d(TAG, "Saved. " + saveTask.toString());

            // Remove the soft keyboard after hitting the save button
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = requireActivity().getCurrentFocus();
            if (currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
//            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            // clear text after saving
            taskTitle.setText("");
            taskDescription.setText("");
            taskDueDate.setText("");
            taskPriority.setSelection(0);
        }
    }

    private void onDelete(Task task) {
        // When clicked, delete the item that was clicked button
        if (task != null) {
            String item = "Delete: " + task.getTitle();
            Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();

            Log.d(TAG, item);

            // remove the object from the array held in the viewModel
            mViewModel.removeTask(task);

            // notifies that the underlying data has changed
            adapter.notifyDataSetChanged();
        }
    }

    private void showTaskAdditionalInformation(Task task) {
        String message = task.getTitle() + " finish date on " + task.getDueDate() + " (priority: " + task.getPriority() + ")";
        Log.d(TAG, message);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showMissingInfoToast(String invalid) {
        String message = "Missing task " + invalid;
        Log.d(TAG, message);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(Task task) {
        // show alertDialog for additional information
        showTaskAdditionalInformation(task);
    }

    @Override
    public void onItemDeleteClicked(Task task) {
        // delete the selected one
        onDelete(task);
    }
}