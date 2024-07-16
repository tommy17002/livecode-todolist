package enigma.to_do_list.service;

import enigma.to_do_list.model.Task;
import enigma.to_do_list.utils.DTO.TaskCompleteDTO;
import enigma.to_do_list.utils.DTO.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface TaskService {
    Task create(TaskDTO request);
    Page<Task> getAll(Pageable pageable, Boolean completed, Date due_date);
    Task getOne(Integer id);
    Task update(Integer id, TaskDTO request);
    Task completed(TaskCompleteDTO request);
    void delete(Integer id);
}
