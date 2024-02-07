package SpringDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 1. Добавление задачи:
 * POST localhost:8080/api/tasks
 * 2. Просмотр всех задач:
 * GET localhost:8080/api/tasks
 * 3. Просмотр задач по статусу (например, "завершена", "в процессе", "не начата").
 * GET localhost:8080/api/tasks/filter/status
 * 4. Изменение статуса задачи.
 * PUT localhost:8080/api/tasks/update/id
 * 5. Удаление задачи.
 * DELETE localhost:8080/api/tasks/id
 * ДОПОЛНИТЕЛЬНО:
 * 6. Сортировка по id:
 * localhost:8080/api/tasks/sort
 * 7. найти задачу по id:
 * localhost:8080/api/tasks/id
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping
    public List<Task> getAllTask(){
        return taskService.getAllTask();
    }
    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskService.createTask(task);
    }
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @GetMapping("/filter/{status}")
    public List<Task> filterByStatus(@PathVariable Task.Status status) {
        return taskService.filterByStatus(status);
    }
    @GetMapping("/sort")
    public List<Task> sortById(){
        return taskService.sortById();
    }
    @PutMapping("/update/{id}")
    public Task updateTaskByStatus(@PathVariable Long id, @RequestBody Task task){
        return taskService.updateTaskByStatus(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }
}
