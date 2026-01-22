import java.util.Scanner;

public class Jarvis {
    public static void main(String[] args) {
        String name = "Jarvis";
        String horizontalLine = "____________________________________________________________";

        // Greet
        System.out.println(horizontalLine);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);

        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        String command = scanner.nextLine(); // Read the first line of input

        // Loop: Keep reading input until the user types "bye"
        while (!command.equals("bye")) {
            System.out.println(horizontalLine);
            System.out.println(command); // Echo the command
            System.out.println(horizontalLine);

            command = scanner.nextLine(); // Read the next line
        }

        // Exit
        System.out.println(horizontalLine);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);

        scanner.close();
    }
}