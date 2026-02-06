package jarvis.tasks;

import java.util.ArrayList;

/**
 * Represents the list of tasks and handles operations like adding or deleting.
 */

public class TaskList {
    private static ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public void printList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    // Helper to give access to the raw list for storage
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public static java.util.ArrayList<Task> findTasks(String keyword) {
        java.util.ArrayList<Task> matchingTasks = new java.util.ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}