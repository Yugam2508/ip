package jarvis;

import jarvis.parser.CommandType;
import jarvis.parser.Parser;
import jarvis.storage.Storage;
import jarvis.tasks.Deadline;
import jarvis.tasks.Event;
import jarvis.tasks.Task;
import jarvis.tasks.TaskList;
import jarvis.tasks.Todo;
import jarvis.ui.Ui;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Jarvis is a personal assistant chatbot that helps users manage tasks.
 * It supports Todo, Deadline, and Event tasks and persists data to disk.
 */
public class Jarvis {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a Jarvis chatbot with the specified file path.
     *
     * @param filePath Path to the data file.
     */
    public Jarvis(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            loadedTasks = new TaskList();
        }
        this.tasks = loadedTasks;
    }

    /**
     * Runs the main interaction loop of the chatbot.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            ui.showLine();

            CommandType command = Parser.parseCommand(input);

            try {
                switch (command) {
                    case BYE:
                        isExit = true;
                        break;
                    case LIST:
                        ui.showTaskList(tasks.getAllTasks());
                        break;
                    case MARK:
                        handleMark(input);
                        break;
                    case UNMARK:
                        handleUnmark(input);
                        break;
                    case DELETE:
                        handleDelete(input);
                        break;
                    case TODO:
                        handleTodo(input);
                        break;
                    case DEADLINE:
                        handleDeadline(input);
                        break;
                    case EVENT:
                        handleEvent(input);
                        break;
                    case FIND:
                        handleFind(input);
                        break;
                    case CHEER:
                        ui.printCheer(storage.getRandomQuote());
                        break;
                    default:
                        throw new JarvisException("Unknown command.");
                }
            } catch (JarvisException e) {
                ui.showError(e.getMessage());
            }

            ui.showLine();
        }

        ui.showExit();
    }

    private void handleMark(String input) {
        int index = Parser.parseIndex(input);
        Task task = tasks.get(index);
        task.markAsDone();
        storage.save(tasks.getAllTasks());
        ui.showMark(task);
    }

    private void handleUnmark(String input) {
        int index = Parser.parseIndex(input);
        Task task = tasks.get(index);
        task.markAsNotDone();
        storage.save(tasks.getAllTasks());
        ui.showUnmark(task);
    }

    private void handleDelete(String input) {
        int index = Parser.parseIndex(input);
        Task removed = tasks.deleteTask(index);
        storage.save(tasks.getAllTasks());
        ui.showTaskRemoved(removed, tasks.size());
    }

    private void handleTodo(String input) {
        String description = Parser.parseDescription(input);
        Task todo = new Todo(description);
        tasks.addTask(todo);
        storage.save(tasks.getAllTasks());
        ui.showTaskAdded(todo, tasks.size());
    }

    private void handleDeadline(String input) {
        String[] parts = Parser.parseDeadline(input);
        Task deadline = new Deadline(parts[0], parts[1]);
        tasks.addTask(deadline);
        storage.save(tasks.getAllTasks());
        ui.showTaskAdded(deadline, tasks.size());
    }

    private void handleEvent(String input) {
        String[] parts = Parser.parseEvent(input);
        Task event = new Event(parts[0], parts[1], parts[2]);
        tasks.addTask(event);
        storage.save(tasks.getAllTasks());
        ui.showTaskAdded(event, tasks.size());
    }

    private void handleFind(String input) {
        String keyword = Parser.parseFindKeyword(input);
        List<Task> results = tasks.find(keyword);
        ui.showFindResults(results);
    }

    public static void main(String[] args) {
        new Jarvis("data/jarvis.txt").run();
    }
}