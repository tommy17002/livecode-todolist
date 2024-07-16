package enigma.to_do_list.service.implementation;

import enigma.to_do_list.model.Category;
import enigma.to_do_list.model.Task;
import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.repository.TaskRepository;
import enigma.to_do_list.service.CategoryService;
import enigma.to_do_list.service.TaskService;
import enigma.to_do_list.service.UserService;
import enigma.to_do_list.utils.DTO.TaskCompleteDTO;
import enigma.to_do_list.utils.DTO.TaskDTO;
import enigma.to_do_list.utils.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    public Task create(TaskDTO request) {
        UserEntity user = userService.getOne(request.getUser_id());
        Category category = categoryService.getOne(request.getCategory_id());
        Task task = new Task();
        task.setUser(user);
        task.setCategory(category);
        task.setTo_do(request.getTo_do());
        task.setDue_date(request.getDue_date());
        task.setNotes(request.getNotes());
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getAll(Pageable pageable, Boolean completed, Date due_date) {
        Specification<Task> specification = TaskSpecification.getSpecification(completed, due_date);
        return taskRepository.findAll(specification, pageable);
    }

    @Override
    public Task getOne(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " not found"));
    }

    @Override
    public Task update(Integer id, TaskDTO request) {
        Task updatedTask = this.getOne(id);
        UserEntity updatedUser = userService.getOne(request.getUser_id());
        Category updatedCategory = categoryService.getOne(request.getCategory_id());
        updatedTask.setUser(updatedUser);
        updatedTask.setCategory(updatedCategory);
        updatedTask.setTo_do(request.getTo_do());
        updatedTask.setDue_date(request.getDue_date());
        updatedTask.setNotes(request.getNotes());
        updatedTask.setCompleted(request.getCompleted());
        return taskRepository.save(updatedTask);
    }

    @Override
    public Task completed(TaskCompleteDTO request) {
        Task completedTask = this.getOne(request.getId());
        completedTask.setCompleted(true);
        if (completedTask.getDue_date() != null
                && request.getCompleted_at() != null
                && !completedTask.getNotes().isEmpty()) {
            if (completedTask.getDue_date().getTime() > request.getCompleted_at().getTime()) {
                completedTask.setNotes("YOU'VE DONE YOUR TASK BEFORE DEADLINE, GOOD JOB!");
            } else completedTask.setNotes("YOU MISS THE DEADLINE, BE BETTER NEXT TIME!");
        }
        return taskRepository.save(completedTask);
    }

    @Override
    public void delete(Integer id) {
        taskRepository.deleteById(id);
    }
}
