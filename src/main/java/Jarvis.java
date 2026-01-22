import java.util.Scanner;

public class Jarvis {
    public static void main(String[] args) {
        String name = "Jarvis";
        String horizontalLine = "____________________________________________________________";

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
                    System.out.println((i + 1) + "." + tasks[i].toString());
                }
            } else if (command.startsWith("mark")) {
                int index = Integer.parseInt(command.split(" ")[1]) - 1;
                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index].toString());
            } else if (command.startsWith("unmark")) {
                int index = Integer.parseInt(command.split(" ")[1]) - 1;
                tasks[index].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index].toString());
            } else if (command.startsWith("todo")) {
                String description = command.substring(5); // Remove "todo "
                tasks[taskCount] = new Todo(description);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1].toString());
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (command.startsWith("deadline")) {
                // Format: deadline return book /by Sunday
                String[] parts = command.substring(9).split(" /by ");
                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1].toString());
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (command.startsWith("event")) {
                // Format: event project meeting /from Mon 2pm /to 4pm
                String[] parts = command.substring(6).split(" /from ");
                String description = parts[0];
                String[] timeParts = parts[1].split(" /to ");
                String from = timeParts[0];
                String to = timeParts[1];

                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1].toString());
                System.out.println("Now you have " + taskCount + " tasks in the list.");
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

// Parent Class
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
}

// Child Class: Todo
class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

// Child Class: Deadline
class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

// Child Class: Event
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
}