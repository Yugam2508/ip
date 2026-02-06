package jarvis.ui;

import jarvis.tasks.Task;

import java.util.Scanner;

/**
 * Ui class handles all user interaction.
 */

public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of input from the user.
     * @return The command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm jarvis.Jarvis");
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showExit() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public void showTaskAdded(Task task, int count) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    public void showTaskRemoved(Task task, int count) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println("OOPS!!! Problem loading file. Starting with empty list.");
    }

    public void showFindResults(java.util.ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("____________________________________________________________");
            System.out.println(" No matching tasks found.");
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
            System.out.println("____________________________________________________________");
        }
    }
    public void printCheer(String quote) {
        System.out.println("____________________________________________________________");
        System.out.println(" Hey! Here is a quote for you:");
        System.out.println(" \"" + quote + "\"");
        System.out.println("____________________________________________________________");
    }
}