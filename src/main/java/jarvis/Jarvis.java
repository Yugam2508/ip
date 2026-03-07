package jarvis;

import jarvis.parser.CommandType;
import jarvis.parser.Parser;
import jarvis.storage.Storage;
import jarvis.tasks.Deadline;
import jarvis.tasks.Event;
import jarvis.tasks.JarvisException;
import jarvis.tasks.Task;
import jarvis.tasks.TaskList;
import jarvis.tasks.Todo;
import jarvis.ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Jarvis is a personal assistant chatbot that helps users track tasks.
 * It supports ToDo, Deadline, and Event tasks, and saves data to a local file.
 */
public class Jarvis {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Jarvis instance with the specified file path for data storage.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Jarvis(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the Jarvis chatbot.
     * Continuously reads user commands and executes them until the user exits.
     */
    public void run() {
        assert ui != null : "Ui should be initialized";
        assert storage != null : "Storage should be initialized";
        assert tasks != null : "TaskList should be initialized";
        assert !tasks.getAllTasks().getClass().equals(null) : "Tasks list should be valid";

        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String fullCommand = ui.readCommand();
            ui.showLine();

            CommandType command = Parser.parseCommand(fullCommand);

            try {
                switch (command) {
                    case BYE:
                        isExit = true;
                        break;
                    case LIST:
                        tasks.printList();
                        break;
                    case MARK:
                        int markIndex = Parser.parseIndex(fullCommand);
                        tasks.get(markIndex).markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks.get(markIndex));
                        storage.save(tasks.getAllTasks());
                        break;
                    case UNMARK:
                        int unmarkIndex = Parser.parseIndex(fullCommand);
                        tasks.get(unmarkIndex).markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(unmarkIndex));
                        storage.save(tasks.getAllTasks());
                        break;
                    case DELETE:
                        int delIndex = Parser.parseIndex(fullCommand);
                        Task deleted = tasks.deleteTask(delIndex);
                        ui.showTaskRemoved(deleted, tasks.size());
                        storage.save(tasks.getAllTasks());
                        break;
                    case TODO:
                        if (fullCommand.trim().equals("todo")) {
                            throw new JarvisException("OOPS!!! Empty todo.");
                        }
                        Task todo = new Todo(fullCommand.substring(5).trim());
                        tasks.addTask(todo);
                        ui.showTaskAdded(todo, tasks.size());
                        storage.save(tasks.getAllTasks());
                        break;
                    case DEADLINE:
                        if (fullCommand.trim().equals("deadline")) {
                            throw new JarvisException("OOPS!!! Empty deadline.");
                        }
                        String[] dParts = fullCommand.substring(9).split(" /by ");
                        Task deadline = new Deadline(dParts[0].trim(),
                                dParts[1].trim());
                        tasks.addTask(deadline);
                        ui.showTaskAdded(deadline, tasks.size());
                        storage.save(tasks.getAllTasks());
                        break;
                    case EVENT:
                        if (fullCommand.trim().equals("event")) {
                            throw new JarvisException("OOPS!!! Empty event.");
                        }
                        String[] eParts = fullCommand.substring(6).split(" /from ");
                        String[] tParts = eParts[1].split(" /to ");
                        Task event = new Event(eParts[0].trim(),
                                tParts[0].trim(), tParts[1].trim());
                        tasks.addTask(event);
                        ui.showTaskAdded(event, tasks.size());
                        storage.save(tasks.getAllTasks());
                        break;
                    case FIND:
                        String[] fParts = fullCommand.split(" ", 2);
                        if (fParts.length < 2 || fParts[1].trim().isEmpty()) {
                            ui.showError("The search keyword cannot be empty.");
                        } else {
                            String keyword = fParts[1].trim();
                            ArrayList<Task> foundTasks = tasks.findTasks(keyword);
                            ui.showFindResults(foundTasks);
                        }
                        break;
                    case CHEER:
                        ui.printCheer(getRandomQuote());
                        break;
                    default:
                        throw new JarvisException(
                                "OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (JarvisException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("OOPS!!! Something went wrong: " + e.getMessage());
            }
            ui.showLine();
        }
        ui.showExit();
    }

    /**
     * Generates a response for the given user input.
     *
     * @param input The user's input command.
     * @return The response from Jarvis.
     */
    public String getResponse(String input) {
        CommandType command = Parser.parseCommand(input);
        StringBuilder response = new StringBuilder();

        try {
            switch (command) {
            case BYE:
                return "Goodbye, sir. I'll be here when you need me.";
            case LIST:
                if (tasks.size() == 0) {
                    return "Your task list is empty, sir. Quite refreshing, isn't it?";
                }
                response.append("Here are your tasks, sir:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    response.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
                }
                return response.toString();
            case MARK:
                int markIndex = Parser.parseIndex(input);
                if (markIndex >= tasks.size()) {
                    throw new JarvisException("Task number " + (markIndex + 1)
                            + " doesn't exist, sir. You have " + tasks.size() + " tasks.");
                }
                tasks.get(markIndex).markAsDone();
                storage.save(tasks.getAllTasks());
                return "Excellent work, sir. I've marked this task as complete:\n  "
                        + tasks.get(markIndex);
            case UNMARK:
                int unmarkIndex = Parser.parseIndex(input);
                if (unmarkIndex >= tasks.size()) {
                    throw new JarvisException("Task number " + (unmarkIndex + 1)
                            + " doesn't exist, sir. You have " + tasks.size() + " tasks.");
                }
                tasks.get(unmarkIndex).markAsNotDone();
                storage.save(tasks.getAllTasks());
                return "Understood, sir. I've unmarked this task:\n  "
                        + tasks.get(unmarkIndex);
            case DELETE:
                int delIndex = Parser.parseIndex(input);
                if (delIndex >= tasks.size()) {
                    throw new JarvisException("Task number " + (delIndex + 1)
                            + " doesn't exist, sir. You have " + tasks.size() + " tasks.");
                }
                Task deleted = tasks.deleteTask(delIndex);
                storage.save(tasks.getAllTasks());
                return "Task removed from your schedule, sir:\n  " + deleted
                        + "\nYou now have " + tasks.size() + " tasks remaining.";
            case TODO:
                if (input.trim().equals("todo")) {
                    throw new JarvisException("OOPS!!! I need a description for this task, sir.");
                }
                Task todo = new Todo(input.substring(5).trim());
                tasks.addTask(todo);
                storage.save(tasks.getAllTasks());
                return "Task registered, sir:\n  " + todo
                        + "\nYou now have " + tasks.size() + " tasks in your schedule.";
            case DEADLINE:
                if (input.trim().equals("deadline")) {
                    throw new JarvisException("OOPS!!! I need a deadline description, sir.");
                }
                String[] dParts = input.substring(9).split(" /by ");
                if (dParts.length < 2 || dParts[1].trim().isEmpty()) {
                    throw new JarvisException(
                            "Please specify a deadline using /by, sir.\nExample: deadline return book /by 2024-12-25");
                }
                Task deadline = new Deadline(dParts[0].trim(), dParts[1].trim());
                tasks.addTask(deadline);
                storage.save(tasks.getAllTasks());
                return "Deadline noted, sir:\n  " + deadline
                        + "\nYou now have " + tasks.size() + " tasks in your schedule.";
            case EVENT:
                if (input.trim().equals("event")) {
                    throw new JarvisException("OOPS!!! I need event details, sir.");
                }
                String[] eParts = input.substring(6).split(" /from ");
                if (eParts.length < 2) {
                    throw new JarvisException(
                            "Please specify start time using /from, sir.\nExample: event meeting /from 2pm /to 4pm");
                }
                String[] tParts = eParts[1].split(" /to ");
                if (tParts.length < 2 || tParts[1].trim().isEmpty()) {
                    throw new JarvisException(
                            "Please specify end time using /to, sir.\nExample: event meeting /from 2pm /to 4pm");
                }
                Task event = new Event(eParts[0].trim(), tParts[0].trim(), tParts[1].trim());
                tasks.addTask(event);
                storage.save(tasks.getAllTasks());
                return "Event scheduled, sir:\n  " + event
                        + "\nYou now have " + tasks.size() + " tasks in your schedule.";
            case FIND:
                String[] fParts = input.split(" ", 2);
                if (fParts.length < 2 || fParts[1].trim().isEmpty()) {
                    return "I need a keyword to search for, sir.";
                }
                String keyword = fParts[1].trim();
                ArrayList<Task> foundTasks = tasks.findTasks(keyword);
                if (foundTasks.isEmpty()) {
                    return "No matching tasks found, sir. Perhaps you've already completed them?";
                }
                response.append("I found these matching tasks, sir:\n");
                for (int i = 0; i < foundTasks.size(); i++) {
                    response.append((i + 1)).append(".").append(foundTasks.get(i)).append("\n");
                }
                return response.toString();
            case CHEER:
                return "A bit of motivation for you, sir:\n\"" + getRandomQuote() + "\"";
            case HELP:
                return getHelpMessage();
            default:
                throw new JarvisException(
                        "I'm afraid I don't understand that command, sir.");
            }
        } catch (JarvisException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Something went wrong, sir: " + e.getMessage();
        }
    }

    /**
     * Returns a help message listing all available commands.
     *
     * @return The help message with command descriptions.
     */
    private String getHelpMessage() {
        return "Here are the commands I understand, sir:\n\n"
                + "todo TASK - Add a simple task\n"
                + "deadline TASK /by DATE - Add a task with deadline\n"
                + "event TASK /from START /to END - Add an event\n"
                + "list - Show all tasks\n"
                + "mark NUMBER - Mark task as done\n"
                + "unmark NUMBER - Mark task as not done\n"
                + "delete NUMBER - Remove a task\n"
                + "find KEYWORD - Search for tasks\n"
                + "cheer - Get motivational quote\n"
                + "help - Show this message\n"
                + "bye - Exit the application";
    }

    /**
     * Retrieves a random motivational quote from the cheer.txt file.
     *
     * @return A random motivational quote, or a default message if file cannot be read.
     */
    private String getRandomQuote() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("data/cheer.txt"));
            if (lines.isEmpty()) {
                return "You can do it!";
            }
            int randomIndex = new Random().nextInt(lines.size());
            return lines.get(randomIndex);
        } catch (IOException e) {
            return "Keep pushing forward! (Could not read quote file)";
        }
    }

    /**
     * Main entry point for the Jarvis application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Jarvis("./data/jarvis.txt").run();
    }
}