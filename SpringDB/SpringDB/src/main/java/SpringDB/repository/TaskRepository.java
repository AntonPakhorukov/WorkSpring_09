package SpringDB.repository;

import SpringDB.model.Performer;
import SpringDB.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.status=?1")
    List<Task> filterByStatus(Task.Status status);

    //Не понятно, почему не работает...
//    @Modifying
//    @Transactional
//    @Query("UPDATE Task t SET t.performers = ?1 WHERE t.id = ?2")
//    void addPerformerToTask(Performer performer, Long id);

}
