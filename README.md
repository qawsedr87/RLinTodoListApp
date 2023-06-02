# Todo List

Create a To Do list app. 

## Requirements

<details>
  <summary> Data Structure </summary>

Each item in your list should contain at least 4 pieces of information. These are:

- Task title (display in the RecyclerView)
- Short description of task (display in the RecyclerView)
- Due Date (display in the RecyclerView)
- Additional information about the task (display in dialog when item in list is
touched)
- The longer information can be additional info, dependencies, or anything
you might want to remind yourself about the item.
- You can have more fields if you feel it will add to the usability of your app.


</details>

<details>
  <summary> UI </summary>

Your activity should contain a RecyclerView (showing your ToDo items) and a `data entry` area that allows the user to add tasks. You can put these on the same screen or you can use some technique to launch the entry screen when the user wants to enter more tasks.


- For full credit: Your UI(s) should be easy for the user to read. The data should be easy to enter regardless of how many items are in your To Do list.
- Data entry: This area should allow the user to enter in the task title, short description, due date and additional information about the task.
    - The task title and due date must be entered before the entry can be saved. Do not let the user add a ToDo list without this information.
        - Give feedback to the user (via a Toast) if they try to add a task without the mandatory information.
    - The user should not be able to enter letters for the date field.
    - Let your user “know” what should be entered into each field
        - Hints can be used for this. (Text should not be as it may result in incorrect entries put into the data base.)
    - The short description and additional information are optional for the user; however, your app needs to handle them.
        - If no short description is entered, you can leave this item blank in your list.
        - If no additional information is entered, your popup should indicate there was no additional information.
- Rotation: If the user rotates the phone, the data that was entered should not be lost. Review using ViewModels (see the Zoo exercises)
- RecyclerView - Row format: You may make the layout each row whatever you wish. One possible layout would be to put the title on the top line at the left, the short description on a second line and the due date on the far right.

</details>

<details>
  <summary> Saving & Retrieving the ToDo tasks </summary>

Storing/retrieving the items


- The tasks should be saved in a sql lite db,
- The existing tasks should be reloaded on entry. Read the items from the
database when your app opens to initialize the information in your app.

</details>


<details>
  <summary> Displaying the additional information </summary>

about the list item

- Display the `additional information` in a `dialog` when the list item is clicked.
    - In class we covered setting a listener on a recycler item click. Steps: 
        1. Add a method to the RecyclerViewAdapter callback interface
        2. In the RecyclerViewAdapter onBindView method , attach a click
    listener to the holder and call the interface when the item was clicked.    
        3. In the calling component (the fragment) implement the callback method and do something when the callback method is invoked
        4. In the class exercise, this was a delete. In this assignment, display the additional information in a dialog when the callback is invoked.

- User an alertDialog or another dialog type is fine. Displaying in a toast does not meet the complete requirement.


</details>

<details>
  <summary> Deleting Items </summary>

- Allow the user to remove an element by long clicking on it.
- Handling a long click is similar to handling a click
    - Add a method to the RecyclerViewAdapter callback interface. This means the interface will have two methods. one to call when data is to be displayed, one to call for delete. As an example, extending the RecyclerView adapter made in module 8 for the Zoo app.
    
    ```java
        public interface OnAdapterItemInteraction { 
            void onItemSelected(Animal animal); 
            void onItemLongClick(Animal animal);
        }
    ```

    - In the RecyclerViewAdapter onBindView method, attach a long click listener to the holder and call the interface when the item was clicked. Attaching a long click listener is similar to attaching the onClickListener

    ```java
    holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
        
        @Override
        public boolean onLongClick(View v) { 
            if (null != mListener) {
                mListener. onItemLongClick (animal); 
            }
            return true; 
        }
    });
    ```

    - In the calling component (the fragment) implement the callback method and do something when the callback method is invoked

</details>


### Code 
your app should demonstrate the techniques used in class. These should include

- RecyclerView and RecyclerView.Adapter
- Sqlite database
- ConstraintLayouts
- ViewModel to hold the data
    

## Installation
You can clone the project with Android Studio Electric Eel.

```
git clone git@github.com:qawsedr87/RLinTodoListApp.git
cd RLinTodoListApp
```


## How to test
- Add a task.
    - The task title and due date must be entered before the task can be added.
        - The task is added to both the visual list and to the sqlite database
    - If the task title or due date are not entered, useful feedback must be given to the user
- Click on a task to get additional information. The additional information should be displayed in a dialog
- Delete a task by long clicking
    - The task should be removed from the visual list and from the sqlite database
- Rotate the phone
    - The data should remain when phone is rotated.
- Verify your sqlite database works
    - After entering a couple of todo’s exit your app (via the back key).
    - Restart your app and verify that the items you entered are still present (eg they have been retrieved from the sqlite database).


After you complete your project, take a screenshot showing your running app with 4 or more “To Do” items in your list. Delete one item, exit your app and restart it and show a 2nd screenshot showing the list of persisted ToDo items.

## Demo

part of screenshots


#### todo_list
<img src="/screenshots/3_todo_list.png" width="234" height="416">

#### todo_alertdialog
<img src="/screenshots/4_selected_task_info_alertdialog.png" width="234" height="416">