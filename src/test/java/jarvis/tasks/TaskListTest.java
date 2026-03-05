package jarvis.tasks;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void testAddTask() {
        TaskList tasks = new TaskList();
        Task t = new Todo("read book");
        tasks.addTask(t);
        assertEquals(1, tasks.size());
    }

    @Test
    public void testFindTasks() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("read book"));
        tasks.addTask(new Todo("return book"));
        tasks.addTask(new Todo("buy pen"));
        ArrayList<Task> found = tasks.findTasks("book");
        assertEquals(2, found.size());
    }
}