package SpringDB;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 1. Добавление задачи.
 * 2. Просмотр всех задач.
 * 3. Просмотр задач по статусу (например, "завершена", "в процессе", "не начата").
 * 4. Изменение статуса задачи.
 * 5. Удаление задачи.
 */
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private PerformerService performerService;

    @TrackUserAction
    public Task createTask(Task task) {
        task.setDate(String.valueOf(Calendar.getInstance().getTime()));
        return taskRepository.save(task);
    }

    @TrackUserAction
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @TrackUserAction
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No task with id: " + id));
    }

    @TrackUserAction
    public List<Task> filterByStatus(Task.Status status) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @TrackUserAction
    public Task updateTaskByStatus(Long id, Task task) {
        taskRepository.findById(id).get().setStatus(task.getStatus());
        return taskRepository.save(taskRepository.findById(id).get());
    }

    @TrackUserAction
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @TrackUserAction
    public List<Task> sortById() {
        return taskRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getId))
                .collect(Collectors.toList());
    }

    @TrackUserAction
    public Task assignPerformerToTask(Long id, Long performerId) {
        Task existingTask = getTaskById(id); // Нашли задачу
        Performer performer = performerService.findPerformerById(performerId); // нашли исполнителя
        existingTask.getPerformers().add(performer); // у задачи добавили исполнителя в список исполнителей


        return taskRepository.save(existingTask);
    }

    @TrackUserAction
    public Task deassingPerformerToTask(Long id, Long performerId) {
        Task existingTask = getTaskById(id);
        existingTask.getPerformers().removeIf(performer -> performer.getId().equals(performerId));
        Performer performer = performerService.findPerformerById(performerId);
        return taskRepository.save(existingTask);
    }

}
