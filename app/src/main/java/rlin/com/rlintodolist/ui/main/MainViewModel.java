package rlin.com.rlintodolist.ui.main;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    public final static String TAG = "TodoListViewModel";

    // database: DBHelper


    List<Task> tasks = new ArrayList<>();
    public List<Task> getTasks() {
        return tasks;
    }

    public Task addTask(String title, String description, String dueDate, String priority) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);

        tasks.add(task);

        // TODO: Database
        long taskId;

        return task;
    }

    public Task removeTask(Task task) {
        tasks.remove(task);

        // TODO: Database

        return task;
    }

    public void init_database(Context context) {
        // TODO
    }
}