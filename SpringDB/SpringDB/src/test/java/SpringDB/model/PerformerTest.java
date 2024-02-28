package SpringDB.model;

import SpringDB.model.Performer;
import SpringDB.model.Task;
import SpringDB.repository.PerformerRepository;
import SpringDB.service.PerformerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PerformerTest {
    @InjectMocks
    private PerformerService performerService;
    @Mock
    private PerformerRepository performerRepository;
    @BeforeEach
    private void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void getIdTest() {
        Performer performer = new Performer();
        performer.setId(1L);
        assert (performer.getId().equals(1L));
    }
    @Test
    public void getNameTest() {
        Performer performer = new Performer();
        performer.setName("perf");
        assert (performer.getName().equals("perf"));
    }
    @Test
    public void getTasksTest() {
        Performer performer = new Performer();
        performer.setTasks(new ArrayList<>(List.of(new Task(), new Task())));
        assert(performer.getTasks().size() == 2);
    }
}
