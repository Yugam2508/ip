package jarvis.ui;

import jarvis.tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles user interface interactions.
 */
public class Ui {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";
    private Scanner scanner;

    /**
     * Constructs a Ui instance and initializes the scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        printLine();
        System.out.println(" Hello! I'm Jarvis");
        System.out.println(" What can I do for you?");
        printLine();
    }

    /**
     * Displays the exit message.
     */
    public void showExit() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Displays an error message for file loading issues.
     */
    public void showLoadingError() {
        System.out.println("No existing data file found. Starting with empty task list.");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays the horizontal line separator.
     */
    public void showLine() {
        printLine();
    }

    /**
     * Prints the horizontal line.
     */
    private void printLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Reads a command from the user.
     *
     * @return The command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task The task that was added.
     * @param size The total number of tasks in the list.
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays a message when a task is removed.
     *
     * @param task The task that was removed.
     * @param size The total number of tasks remaining in the list.
     */
    public void showTaskRemoved(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays the results of a find command.
     *
     * @param foundTasks The list of tasks matching the search criteria.
     */
    public void showFindResults(ArrayList<Task> foundTasks) {
        if (foundTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < foundTasks.size(); i++) {
                System.out.println((i + 1) + "." + foundTasks.get(i));
            }
        }
    }

    /**
     * Displays a motivational quote.
     *
     * @param quote The quote to display.
     */
    public void printCheer(String quote) {
        printLine();
        System.out.println(" \"" + quote + "\"");
        printLine();
    }
}