import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Jarvis is a personal assistant chatbot that helps users track tasks.
 * It supports ToDo, Deadline, and Event tasks, and saves data to a local file.
 */
public class Jarvis {
    private static final String FILE_PATH = "./data/jarvis.txt";

    // Standard: Static variables should be at the top
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    /**
     * Main entry point of the application.
     * Initializes the app, loads data, and enters the command loop.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        String name = "Jarvis";
        String horizontalLine = "____________________________________________________________";

        System.out.println(horizontalLine);
        loadData();
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!command.equals("bye")) {
            System.out.println(horizontalLine);

            try {
                if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i].toString());
                    }

                } else if (command.startsWith("mark")) {
                    int index = Integer.parseInt(command.split(" ")[1]) - 1;
                    tasks[index].markAsDone();
                    saveData();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[index].toString());

                } else if (command.startsWith("unmark")) {
                    int index = Integer.parseInt(command.split(" ")[1]) - 1;
                    tasks[index].markAsNotDone();
                    saveData();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[index].toString());

                } else if (command.startsWith("delete")) {
                    int index = Integer.parseInt(command.split(" ")[1]) - 1;

                    if (index < 0 || index >= taskCount) {
                        throw new JarvisException("OOPS!!! The task index provided is invalid.");
                    }

                    Task removedTask = tasks[index];

                    for (int i = index; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }

                    taskCount--;
                    saveData();

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask.toString());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                } else if (command.startsWith("todo")) {
                    if (command.trim().equals("todo")) {
                        throw new JarvisException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    String description = command.substring(5);
                    tasks[taskCount] = new Todo(description);
                    taskCount++;
                    saveData();
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1].toString());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                } else if (command.startsWith("deadline")) {
                    if (command.trim().equals("deadline")) {
                        throw new JarvisException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String[] parts = command.substring(9).split(" /by ");
                    tasks[taskCount] = new Deadline(parts[0], parts[1]);
                    taskCount++;
                    saveData();
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1].toString());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                } else if (command.startsWith("event")) {
                    if (command.trim().equals("event")) {
                        throw new JarvisException("OOPS!!! The description of an event cannot be empty.");
                    }
                    String[] parts = command.substring(6).split(" /from ");
                    String description = parts[0];
                    String[] timeParts = parts[1].split(" /to ");
                    tasks[taskCount] = new Event(description, timeParts[0], timeParts[1]);
                    taskCount++;
                    saveData();
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1].toString());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                } else {
                    throw new JarvisException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

            } catch (JarvisException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("OOPS!!! Something went wrong: " + e.getMessage());
            }

            System.out.println(horizontalLine);
            command = scanner.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
        scanner.close();
    }

    private static void saveData() {
        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile();

            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < taskCount; i++) {
                writer.write(tasks[i].toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("OOPS!!! An error occurred while saving tasks.");
        }
    }

    private static void loadData() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return;
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task = null;
                if (type.equals("T")) {
                    task = new Todo(description);
                } else if (type.equals("D")) {
                    task = new Deadline(description, parts[3]);
                } else if (type.equals("E")) {
                    task = new Event(description, parts[3], parts[4]);
                }

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks[taskCount] = task;
                    taskCount++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("OOPS!!! Data file not found. Starting with an empty list.");
        }
    }
}

// ------------------------------------
// Custom Exception
// ------------------------------------
class JarvisException extends Exception {
    public JarvisException(String message) {
        super(message);
    }
}

// ------------------------------------
// Task Classes
// ------------------------------------
class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String toFileFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }
}

class Deadline extends Task {
    protected String by;
    protected LocalDate date;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        try {
            // Level-8: Try to parse the date string (expecting yyyy-MM-dd)
            this.date = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            // If parsing fails (e.g., user entered "Sunday"), keep it as null
            this.date = null;
        }
    }

    @Override
    public String toString() {
        if (date != null) {
            // Level-8: Print nicely as "MMM dd yyyy" (e.g., Oct 15 2019)
            return "[D]" + super.toString() + " (by: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
        } else {
            // Fallback for non-standard dates (e.g., "Sunday")
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    @Override
    public String toFileFormat() {
        // Save it in the original format (by) so loading works correctly
        return "D | " + super.toFileFormat() + " | " + by;
    }
}

class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + from + " | " + to;
    }
}