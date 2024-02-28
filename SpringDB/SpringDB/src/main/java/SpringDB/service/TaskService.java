package SpringDB.service;

import SpringDB.aspect.TrackUserAction;
import SpringDB.model.Performer;
import SpringDB.model.Task;
import SpringDB.repository.PerformerRepository;
import SpringDB.repository.TaskRepository;
import jakarta.transaction.Transactional;
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
    private PerformerRepository performerRepository;

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
        return taskRepository.filterByStatus(status);
    }

    @TrackUserAction
    public Task updateStatusInTask(Long id, Task task) {
        Task currentTask = getTaskById(id);
        currentTask.setStatus(task.getStatus());
        return taskRepository.save(currentTask);
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
    @Transactional
    public Task assignPerformerToTask(Long taskId, Long performerId) {
        Task existingTask = getTaskById(taskId); // Нашли задачу
        Performer performer = performerRepository.findById(performerId).orElse(null); // нашли исполнителя
        existingTask.getPerformers().add(performer); // у задачи добавили исполнителя в список исполнителей
        return taskRepository.save(existingTask);
    }

    @TrackUserAction
    public Task deassingPerformerToTask(Long id, Long performerId) {
        Task existingTask = getTaskById(id);
        existingTask.getPerformers().removeIf(performer -> performer.getId().equals(performerId));
        Performer performer = performerRepository.findById(performerId).orElse(null);
        return taskRepository.save(existingTask);
    }

}
