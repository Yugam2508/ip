package jarvis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the loading and saving of task data to the hard disk.
 */

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
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
                if (isDone) task.markAsDone();
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves the current list of tasks to the file.
     * @param tasks The list of tasks to save.
     */

    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);

            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}