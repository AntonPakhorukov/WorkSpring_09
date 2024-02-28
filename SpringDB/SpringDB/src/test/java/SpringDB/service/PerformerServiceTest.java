package SpringDB.service;

import SpringDB.model.Performer;
import SpringDB.repository.PerformerRepository;
import SpringDB.repository.TaskRepository;
import SpringDB.service.PerformerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.convert.PeriodFormat;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PerformerServiceTest {
    @InjectMocks
    private PerformerService performerService;
    @Mock
    private PerformerRepository performerRepository;
    @Mock
    private TaskRepository taskRepository;

    @Test
    public void findAllTest() {
        Performer performer = new Performer();
        performerRepository.save(performer);

        List<Performer> expected = Collections.singletonList(performer);
        when(performerRepository.findAll()).thenReturn(expected);

        List<Performer> actual = performerService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void findPerformerByIdTest() {
        Performer performer = new Performer();
        performer.setId(1L);

        when(performerRepository.findById(1L)).thenReturn(Optional.of(performer));

        Performer currentPerformer = performerService.findPerformerById(1L);

        assertEquals(performer, currentPerformer);

    }

    @Test
    public void saveTest() {
        Performer performer = new Performer();

        performerService.save(performer);

        Mockito.verify(performerRepository, Mockito.times(1)).save(performer);

    }

    @Test
    public void updatePerformerNameTest() {
        Performer performer = new Performer();
        performer.setId(1L);
        performer.setName("per_01");

        when(performerRepository.findById(1L)).thenReturn(Optional.of(performer));

        Performer actualNamePerf = new Performer();
        actualNamePerf.setName("newName");

        performerService.updatePerformerName(1L, actualNamePerf);

        assertEquals(performer.getName(), actualNamePerf.getName());

    }

    @Test
    public void deletePerformerTest() {
        performerService.deletePerformer(1L);
        Mockito.verify(performerRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void clearListPerformerTest() {
        performerService.clearListPerformer();
        Mockito.verify(performerRepository, times(1)).deleteAll();
    }
}
