package tasks.model;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class TasksArrayTaskListTest {

//    @Mock
//    private ArrayTaskList tasks;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void addTest() throws ParseException {
//        Task task = getMockTask("new task",Task.getDateFormat().parse("2025-02-12 10:10"));
//        Task task2 = getMockTask("new task2",Task.getDateFormat().parse("2025-02-12 10:10"));
//        Mockito.when(tasks.getAll()).thenReturn(Arrays.asList(task));
//        Mockito.doNothing().when(tasks).add(task2);
//
//        this.tasks.add(task2);
//
//        Mockito.verify(tasks, times(1)).add(task2);
//        Mockito.verify(tasks, never()).getAll();
//
//        assert this.tasks.getAll().size() == 1;
//
//        Mockito.verify(tasks, times(1)).getAll();
//        assertEquals("new task", tasks.getAll().get(0).getTitle());
//    }
//
//    @Test
//    public void removeTest() throws ParseException {
//        Task task1 = getMockTask("new task", Task.getDateFormat().parse("2025-02-12 10:10"));
//        Task task2 = getMockTask("new task2", Task.getDateFormat().parse("2025-02-12 10:10"));
//
//        Mockito.when(tasks.getAll()).thenReturn(Arrays.asList(task1, task2));
//        Mockito.when(tasks.remove(task1)).thenReturn(true);
//
//        boolean result = tasks.remove(task1);
//        assert result;
//
//        Mockito.verify(tasks, times(1)).remove(task1);
//        Mockito.verify(tasks, never()).getAll();
//        Mockito.verifyNoMoreInteractions(tasks);
//
//        Mockito.when(tasks.getAll()).thenReturn(Arrays.asList(task2));
//        assert tasks.getAll().size() == 1;
//        assertEquals("new task2", tasks.getAll().get(0).getTitle());
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    private Task getMockTask(String title, Date time) {
//        Task task = Mockito.mock(Task.class);
//
//        Mockito.when(task.getTitle()).thenReturn(title);
//        Mockito.when(task.getTime()).thenReturn(time);
//
//        return task;
//    }

    @Test
    void addAndRemoveTaskTest() throws ParseException {
        ArrayTaskList realList = new ArrayTaskList();
        Task task1 = new Task("task 1", Task.getDateFormat().parse("2025-02-12 10:10"));
        Task task2 = new Task("task 2", Task.getDateFormat().parse("2025-02-12 12:00"));

        realList.add(task1);
        realList.add(task2);

        assertEquals(2, realList.size());
        assertTrue(realList.getAll().contains(task1));
        assertTrue(realList.remove(task1));
        assertEquals(1, realList.size());
        assertFalse(realList.getAll().contains(task1));
    }

}