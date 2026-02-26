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