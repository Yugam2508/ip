import java.util.Scanner;

public class Jarvis {
    public static void main(String[] args) {
        String name = "Jarvis";
        String horizontalLine = "____________________________________________________________";

        // Task storage (Simple array for Level-2)
        String[] tasks = new String[100];
        int taskCount = 0;

        // Greet
        System.out.println(horizontalLine);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!command.equals("bye")) {
            System.out.println(horizontalLine);

            if (command.equals("list")) {
                // Display the list
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                // Add to list
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("added: " + command);
            }

            System.out.println(horizontalLine);
            command = scanner.nextLine();
        }

        // Exit
        System.out.println(horizontalLine);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);

        scanner.close();
    }
}