package tasks.model;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class TasksOperationsTest {
    //private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Test
    public void testCase1() {
        // Define a single non-repetitive task that starts at 03/04/2024 12:00
        Task task = new Task("Task1", new Date(2024 - 1900, 3, 3, 12, 0)); // Task cu start la 03/04/2024 12:00
        task.setActive(true);

        // Add task to the task list
        TasksOperations tasksOperations = new TasksOperations(FXCollections.observableArrayList(task));

        // Define an invalid date range where start is after end
        Date start = new Date(2024 - 1900, 3, 2, 12, 0); // 02/04/2024 12:00
        Date end = new Date(2024 - 1900, 3, 1, 12, 0); // 01/04/2024 12:00

        // Call the incoming method
        Iterable<Task> result = tasksOperations.incoming(start, end);

        // Assert that the result is empty because the date range is invalid
        assertFalse(result.iterator().hasNext());
    }


    @Test
    public void testCase2() {
        // Empty task list
        TasksOperations tasksOperations = new TasksOperations(FXCollections.observableArrayList());

        // Define start and end times
        Date start = new Date(2025 - 1900, 3, 2, 12, 0); // 02/04/2025 12:00
        Date end = new Date(2025 - 1900, 3, 3, 12, 0); // 03/04/2025 12:00

        Iterable<Task> result = tasksOperations.incoming(start, end);

        // Assert that the result is an empty list because there are no tasks
        assertFalse(result.iterator().hasNext());
    }


    @Test
    public void testCase3() {
        // Define a non-repetitive task
        Task task = new Task("Task1", new Date(2025 - 1900, 3, 2, 12, 0)); // 02/04/2025 12:00
        task.setActive(true);

        // Add task to the task list
        TasksOperations tasksOperations = new TasksOperations(FXCollections.observableArrayList(task));

        // Define start and end times
        Date start = new Date(2025 - 1900, 3, 2, 12, 0); // 02/04/2025 12:00
        Date end = new Date(2025 - 1900, 3, 3, 12, 0); // 03/04/2025 12:00

        Iterable<Task> result = tasksOperations.incoming(start, end);

        // Assert that the task is in the result
        assertFalse(result.iterator().hasNext());
    }


    @Test
    public void testCase4() {
        // Define a repetitive task with a start time after the range
        Task task = new Task("Task2", new Date(2025 - 1900, 3, 4, 12, 0), new Date(2025 - 1900, 3, 4, 12, 0), 3600); // Repetitiv începând cu 04/04/2025
        task.setActive(true);

        // Add task to the task list
        TasksOperations tasksOperations = new TasksOperations(FXCollections.observableArrayList(task));

        // Define start and end times
        Date start = new Date(2025 - 1900, 3, 2, 12, 0); // 02/04/2025 12:00
        Date end = new Date(2025 - 1900, 3, 3, 12, 0); // 03/04/2025 12:00

        Iterable<Task> result = tasksOperations.incoming(start, end);

        // Assert that the task is not included since it starts after the range
        assertFalse(result.iterator().hasNext());
    }

    @Test
    public void testCase5() {
        // Define a repetitive task starting at 04/04/2025 12:00
        Task task = new Task("Task3", new Date(2025 - 1900, 3, 4, 12, 0), new Date(2025 - 1900, 3, 4, 12, 0), 3600); // Repetitiv începând cu 04/04/2025
        task.setActive(true);

        // Add task to the task list
        TasksOperations tasksOperations = new TasksOperations(FXCollections.observableArrayList(task));

        // Define start and end times
        Date start = new Date(2025 - 1900, 3, 2, 12, 0); // 02/04/2025 12:00
        Date end = new Date(2025 - 1900, 3, 4, 12, 0); // 04/04/2025 12:00

        Iterable<Task> result = tasksOperations.incoming(start, end);

        // Assert that the task is included since it starts exactly at the end of the range
        assertTrue(result.iterator().hasNext());
        Task retrievedTask = result.iterator().next();
        assertEquals("Task3", retrievedTask.getTitle());
    }

    @Test
    public void testCase6() {
        // Define two repetitive tasks starting at 03/04/2025 12:00
        Task task1 = new Task("Task4", new Date(2025 - 1900, 3, 3, 12, 0), new Date(2025 - 1900, 3, 3, 12, 0), 3600); // Repetitiv începând cu 03/04/2025
        Task task2 = new Task("Task5", new Date(2025 - 1900, 3, 3, 12, 0), new Date(2025 - 1900, 3, 3, 12, 0), 3600); // Repetitiv începând cu 03/04/2025
        task1.setActive(true);
        task2.setActive(true);

        // Add tasks to the task list
        TasksOperations tasksOperations = new TasksOperations(FXCollections.observableArrayList(task1, task2));

        // Define start and end times
        Date start = new Date(2025 - 1900, 3, 2, 12, 0); // 02/04/2025 12:00
        Date end = new Date(2025 - 1900, 3, 4, 12, 0); // 04/04/2025 12:00

        Iterable<Task> result = tasksOperations.incoming(start, end);

        // Assert that both tasks are included since they start at 03/04/2025 (within the range)
        List<Task> tasksList = StreamSupport.stream(result.spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(2, tasksList.size());
        assertTrue(tasksList.stream().anyMatch(task -> task.getTitle().equals("Task4")));
        assertTrue(tasksList.stream().anyMatch(task -> task.getTitle().equals("Task5")));
    }


}
