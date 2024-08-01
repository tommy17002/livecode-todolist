package enigma.to_do_list.repository;

import enigma.to_do_list.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
//public interface TaskRepository extends JpaRepository<Task, String>, JpaSpecificationExecutor<Task> {

}
