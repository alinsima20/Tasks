package tasks.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ECP_BVATest {

//    private Task task;
//
//    @BeforeEach
//    void setUp() {
//        try {
//            task=new Task("new task",Task.getDateFormat().parse("2023-02-12 10:10"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testTaskCreation() throws ParseException {
//       assert task.getTitle() == "new task";
//        System.out.println(task.getFormattedDateStart());
//        System.out.println(task.getDateFormat().format(Task.getDateFormat().parse("2023-02-12 10:10")));
//       assert task.getFormattedDateStart().equals(task.getDateFormat().format(Task.getDateFormat().parse("2023-02-12 10:10")));
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    @DisplayName("TC1_ECP - Task valid cu descriere standard")
    void testECP1() {
        Task task = new Task("Test1", new Date(123, 11, 2), new Date(124, 2, 28), 1);
        assertNotNull(task);
    }

    @Test
    @DisplayName("TC2_ECP - Task invalid cu descriere lunga")
    void testECP2() {
        String longDesc = "p5nYi8XMjcMimjLwrY7vrCaimcV2GpwJcRcaWaqwABUL2hgytPuxN46w97KXzUJKFkyqhEPAdn80uhhvfYraUmdPV0GkKJz9fVeZhwuMFyarLxQam4rwPpRzZ1d412dmDWKrXmV3yHNDxtheeeM4UU0BVw2SNh7Lkp4QMqy8QqxDGwuGgN3qaJwYyJaF1fvP75XtfciePNLNJ6hdxiHAaxPNT3ENkkCPzpmE0T0b57dLbSZdUWTTHQbyX6NWHDUq";
        assertThrows(IllegalArgumentException.class, () -> new Task(longDesc, new Date(123, 3, 12), new Date(124, 2, 28), 1));
    }

    @Test
    @DisplayName("TC3_ECP - Task valid cu start date minim (2000-01-01)")
    void testECP3() {
        Task task = new Task("Test2", new Date(100, 0, 1), new Date(124, 2, 30), 1);
        assertNotNull(task);
    }

    @Test
    @DisplayName("TC4_ECP - Task invalid cu start date in dupa incheierea anului 2025")
    void testECP4() {
        assertThrows(IllegalArgumentException.class, () -> new Task("Test3", new Date(126, 5, 9), new Date(124, 2, 31), 1));
    }

    // Testele BVA (Boundary Value Analysis)

    @Test
    @DisplayName("TC1_BVA - Task invalid cu descriere goala")
    void testBVA1() {
        assertThrows(IllegalArgumentException.class, () -> new Task("", new Date(102, 3, 21), new Date(124, 2, 31), 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"T", "A", "B"})
    @DisplayName("TC2_BVA - Task valid cu descriere de o litera")
    void testBVA2(String description) {
        Task task = new Task(description, new Date(102, 3, 22), new Date(124, 11, 31), 1);
        assertNotNull(task);
    }

    @Test
    @DisplayName("TC3_BVA - Task invalid cu descriere cu 256 caractere")
    void testBVA3() {
        String longDesc = "p5nYi8XMjcMimjLwrY7vrCaimcV2GpwJcRcaWaqwABUL2hgytPuxN46w97KXzUJKFkyqhEPAdn80uhhvfYraUmdPV0GkKJz9fVeZhwuMFyarLxQam4rwPpRzZ1d412dmDWKrXmV3yHNDxtheeeM4UU0BVw2SNh7Lkp4QMqy8QqxDGwuGgN3qaJwYyJaF1fvP75XtfciePNLNJ6hdxiHAaxPNT3ENkkCPzpmE0T0b57dLbSZdUWTTHQbyX6NWHDUq";
        assertThrows(IllegalArgumentException.class, () -> new Task(longDesc, new Date(102, 3, 23), new Date(124, 11, 31), 1));
    }

    @Test
    @DisplayName("TC4_BVA - Task valid cu descriere maxima 255 caractere")
    void testBVA4() {
        String longDesc = "p5nYi8XMjcMimjLwrY7vrCaimcV2GpwJcRcaWaqwABUL2hgytPuxN46w97KXzUJKFkyqhEPAdn80uhhvfYraUmdPV0GkKJz9fVeZhwuMFyarLxQam4rwPpRzZ1d412dmDWKrXmV3yHNDxtheeeM4UU0BVw2SNh7Lkp4QMqy8QqxDGwuGgN3qaJwYyJaF1fvP75XtfciePNLNJ6hdxiHAaxPNT3ENkkCPzpmE0T0b57dLbSZdUWTTHQbyX6NWHDU";
        Task task = new Task(longDesc, new Date(102, 3, 24), new Date(124, 11, 31), 1);
        assertNotNull(task);
    }

    @Test
    @DisplayName("TC5_BVA - Task invalid cu start date sub limita minima (1999-12-31)")
    void testBVA5() {
        assertThrows(IllegalArgumentException.class, () -> new Task("Test", new Date(99, 11, 31), new Date(124, 11, 31), 1));
    }

    @Test
    @DisplayName("TC6_BVA - Task valid cu start date exact la limita minima (2000-01-01)")
    void testBVA6() {
        Task task = new Task("Test", new Date(100, 0, 1), new Date(124, 11, 31), 1);
        assertNotNull(task);
    }

    @Test
    @DisplayName("TC7_BVA - Task invalid cu start date după limita maxima (2026-01-01)")
    void testBVA7() {
        assertThrows(IllegalArgumentException.class, () -> new Task("Test", new Date(126, 1, 1), new Date(124, 11, 31), 1));
    }

    @Test
    @DisplayName("TC8_BVA - Task valid cu start date exact la limita maxima (2025-12-31)")
    void testBVA8() {
        Task task = new Task("Test", new Date(125, 11, 31), new Date(124, 11, 31), 1);
        assertNotNull(task);
    }
}