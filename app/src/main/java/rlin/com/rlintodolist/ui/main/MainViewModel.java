package rlin.com.rlintodolist.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    public final static String TAG = "TodoListViewModel";

    private DBHelper dbHelper = null;

    private void init(Context context) throws Exception {
        dbHelper = new DBHelper(context.getApplicationContext());
    }

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

        long taskId;
        // database
        if (dbHelper != null) {
            taskId = dbHelper.insert(task);
            task.setId(taskId);

            Log.d(TAG, " Saved. Task Id: " + task.getId());
        }

        return task;
    }

    public void removeTask(Task task) {
        tasks.remove(task);

        // database
        if (dbHelper != null) {
            Log.d(TAG, " Deleting Task Id: " + task.getId());
            dbHelper.deleteTaskById(task.getId());
        }
    }

    public void init_database(Context context) {
        try {
            // check if has database instance
            if (dbHelper == null) {
                Log.d(TAG, " init_database: DBHelper null, create new one");

                dbHelper = new DBHelper(context);
                tasks = dbHelper.selectAll();
            } else {
                Log.d(TAG, " init_database: DBHelper already exists");
            }

            if (!tasks.isEmpty()) {
                Log.d(TAG, " tasks list is not empty size: " + tasks.size());
            }

        } catch (Exception e) {
            Log.e(TAG, " init_database: DBHelper threw exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}