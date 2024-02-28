package SpringDB.model;

import SpringDB.repository.TaskRepository;
import SpringDB.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    private void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getIdTest() {
        Task task = new Task();
        task.setId(1L);
        assert (task.getId().equals(1L));
    }

    @Test
    public void getDescriptionTest() {
        Task task = new Task();
        task.setDescription("desc");
        assert (task.getDescription().equals("desc"));
    }

    @Test
    public void getStatusTest() {
        Task task = new Task();
        task.setStatus(Task.Status.ToDo);
        assert (task.getStatus().equals(Task.Status.ToDo));
    }

    @Test
    public void getPerformersTest() {
        Task task = new Task();
        task.setPerformers(new ArrayList<>(List.of(new Performer(), new Performer())));
        assert (task.getPerformers().size() == 2);
    }

    @Test
    public void getDateTest() {
        Task task = new Task();
        String date = String.valueOf(Calendar.getInstance().getTime());
        task.setDate(date);
        assert (task.getDate().equals(date));
    }
}
