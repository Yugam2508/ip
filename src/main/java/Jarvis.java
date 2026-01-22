import java.util.Scanner;

public class Jarvis {
    public static void main(String[] args) {
        String name = "Jarvis";
        String horizontalLine = "____________________________________________________________";

        // Use the Task class array instead of String array
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(horizontalLine);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!command.equals("bye")) {
            System.out.println(horizontalLine);

            if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    // Display status icon [X] or [ ]
                    System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].description);
                }
            } else if (command.startsWith("mark")) {
                // Extract the number (e.g., "mark 2" -> 2)
                int index = Integer.parseInt(command.split(" ")[1]) - 1;
                tasks[index].markAsDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [" + tasks[index].getStatusIcon() + "] " + tasks[index].description);
            } else if (command.startsWith("unmark")) {
                int index = Integer.parseInt(command.split(" ")[1]) - 1;
                tasks[index].markAsNotDone();

                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  [" + tasks[index].getStatusIcon() + "] " + tasks[index].description);
            } else {
                // Create a new Task object
                tasks[taskCount] = new Task(command);
                taskCount++;
                System.out.println("added: " + command);
            }

            System.out.println(horizontalLine);
            command = scanner.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
        scanner.close();
    }
}

// The new Task Class (Blueprint for a task)
class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }
}