package jarvis.storage;

import jarvis.tasks.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to disk.
 */
public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return tasks;
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String[] parts = scanner.nextLine().split(" \\| ");
            Task task = createTask(parts);
            if (parts[1].equals("1")) {
                task.markAsDone();
            }
            tasks.add(task);
        }

        scanner.close();
        return tasks;
    }

    private Task createTask(String[] parts) {
        switch (parts[0]) {
            case "T":
                return new Todo(parts[2]);
            case "D":
                return new Deadline(parts[2], parts[3]);
            case "E":
                return new Event(parts[2], parts[3], parts[4]);
            default:
                throw new IllegalArgumentException("Invalid task type.");
        }
    }

    public void save(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat()
                        + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public String getRandomQuote() {
        return "Keep going – great engineers persist!";
    }
}