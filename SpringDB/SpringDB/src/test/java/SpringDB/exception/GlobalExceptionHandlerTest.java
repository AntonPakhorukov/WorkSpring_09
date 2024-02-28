package SpringDB.exception;

import SpringDB.repository.TaskRepository;
import SpringDB.service.TaskService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GlobalExceptionHandlerTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @BeforeEach
    private void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void handleResourceNotFoundExceptionTest() {
        boolean exception = false;
        when(taskRepository.findById(1L)).thenThrow(new ResourceNotFoundException("Task not found"));
        try {
            taskService.getTaskById(1L);
        } catch (ResourceNotFoundException e){
            exception = true;
        }
        assertTrue(exception);
    }
}
