package SpringDB.service;

import SpringDB.model.Performer;
import SpringDB.model.Task;
import SpringDB.repository.PerformerRepository;
import SpringDB.repository.TaskRepository;
import SpringDB.service.PerformerService;
import SpringDB.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private PerformerRepository performerRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTaskTest() {
        Task task = new Task();
        taskService.createTask(task);
        Mockito.verify(taskRepository, Mockito.times(1)).save(task);
    }

    @Test
    public void getAllTasksTest() {
        Task task1 = new Task();
        taskRepository.save(task1);

        List<Task> expected = Collections.singletonList(task1);

        when(taskRepository.findAll()).thenReturn(expected);

        List<Task> actual = taskService.getAllTask();

        assertEquals(expected, actual);
    }

    @Test
    public void getTaskByIdTest() {
        Task task = new Task();
        task.setDescription("getTaskByIdOne");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task currentTask = taskService.getTaskById(1L);
        assertEquals(task.getDescription(), currentTask.getDescription());
    }

    @Test
    public void filterByStatusTest() {
        Task task = new Task();
        task.setStatus(Task.Status.InProgress);
        taskService.filterByStatus(task.getStatus());
        Mockito.verify(taskRepository, Mockito.times(1)).filterByStatus(Task.Status.InProgress);
    }

    @Test
    public void updateTaskByStatus() {
        Task task = new Task();
        task.setId(1L);

        Task status = new Task();
        status.setStatus(Task.Status.ToDo);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.updateStatusInTask(1L, status);

        assertEquals(task.getStatus(), status.getStatus());
    }

    @Test
    public void deleteTaskTest() {
        taskService.deleteTask(1L);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);

    }

    @Test
    public void assignPerformerToTaskTest() {
        Task expected = new Task();
        expected.setId(1L);
        taskRepository.save(expected);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(expected));

        Performer performer = new Performer();
        performer.setId(2L);
        performerRepository.save(performer);
        when(performerRepository.findById(2L)).thenReturn(Optional.of(performer));

        List<Performer> performers = new ArrayList<>();
        expected.setPerformers(performers);

        taskService.assignPerformerToTask(1L, 2L);

        assert (expected.getPerformers().size() == 1);
    }

    @Test
    public void deassignPerformerToTask() {
        Task task = new Task();
        task.setId(1L);
        task.setPerformers(new ArrayList<>());
        taskRepository.save(task);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Performer performer = new Performer();
        performer.setId(2L);
        performerRepository.save(performer);
        when(performerRepository.findById(2L)).thenReturn(Optional.of(performer));

        task.getPerformers().add(performer);

        taskService.deassingPerformerToTask(1L, 2L);

        assert (task.getPerformers().size() == 0);
        assertEquals(taskService.getTaskById(1L), task);
    }

}
