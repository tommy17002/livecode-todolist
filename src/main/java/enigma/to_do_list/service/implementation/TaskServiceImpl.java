package enigma.to_do_list.service.implementation;

//import enigma.to_do_list.model.Category;
import enigma.to_do_list.model.Task;
import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.repository.TaskRepository;
//import enigma.to_do_list.service.CategoryService;
import enigma.to_do_list.repository.UserRepository;
import enigma.to_do_list.service.TaskService;
import enigma.to_do_list.service.UserService;
import enigma.to_do_list.utils.DTO.TaskCompleteDTO;
import enigma.to_do_list.utils.DTO.TaskDTO;
//import enigma.to_do_list.utils.JwtTokenUtil;
import enigma.to_do_list.utils.UnauthorizedException;
import enigma.to_do_list.utils.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
//    private final CategoryService categoryService;
    private final UserService userService;
    private final UserRepository  userRepository;
//    private final JwtTokenUtil jwtTokenUtil;

//    public String getSessionUsername() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            // Handle case when user is not authenticated
//            return null;
//        }
//
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof UserDetails) {
//            return ((UserDetails) principal).getUsername();
//        } else {
//            return principal.toString();
//        }
//    }
//    @Override

//        String authorizationHeader = request.getHeaders().get("Authorization");
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            throw new UnauthorizedException("Missing or invalid authorization token");
//        }
//
//        String token = authorizationHeader.substring(7);
//
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        User user = userService.findByUsername(username);
//
//        Task task = new Task();
//        task.setUser(user);
//        String userId = user.getId();

//    public Task create(TaskDTO request) {
//        UserEntity user = userService.getOne(request.getUser_id());
////        Category category = categoryService.getOne(request.getCategory_id());
//        Task task = new Task();
//        task.setUser(user);
////        task.setCategory(category);
//        task.setTitle(request.getTitle());
//        task.setDescription(request.getDescription());
//        task.setDueDate(request.getDueDate());
//        task.setStatus(false);
////        task.setNotes(request.getNotes());
//        task.setCreatedAt(new Date());
//        return taskRepository.save(task);
//    }

    public Task updateStatus(TaskDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }

        String username = authentication.getName();
        UserEntity updatedUser = userService.findByUsername(username);

        Task updatedTask = new Task();
        updatedTask.setUser(updatedUser);
        updatedTask.setTitle(request.getTitle());
        updatedTask.setDescription(request.getDescription());
        updatedTask.setDueDate(request.getDueDate());
        updatedTask.setStatus(request.getStatus());

        return taskRepository.save(updatedTask);
    }

    public Task create(TaskDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }

        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);

        Task task = new Task();
        task.setUser(user);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
//        task.setStatus(false);
        task.setStatus("not completed");
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdAtString = currentDateTime.format(formatter);
        task.setCreatedAt(new Date());
//        task.setCreatedAt(request.getCreatedAt());

        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getAll(Pageable pageable, Boolean completed, Date due_date) {
        Specification<Task> specification = TaskSpecification.getSpecification(completed, due_date);
        return taskRepository.findAll(specification, pageable);
    }

    @Override
    public Task getOne(Integer id) {
//    public Task getOne(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " not found"));
    }

    @Override
//    public Task update(Integer id, TaskDTO request) {
    public Task update(TaskDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }

        String username = authentication.getName();
        UserEntity updatedUser = userService.findByUsername(username);

        Task updatedTask = new Task();
        updatedTask.setUser(updatedUser);
//        
//        Task updatedTask = this.getOne(id);
//        UserEntity updatedUser = userService.getOne(request.getUser_id());
//        Category updatedCategory = categoryService.getOne(request.getCategory_id());
        updatedTask.setUser(updatedUser);
//        updatedTask.setCategory(updatedCategory);
        updatedTask.setTitle(request.getTitle());
        updatedTask.setDescription(request.getDescription());
        updatedTask.setDueDate(request.getDueDate());
//        updatedTask.setNotes(request.getNotes());
        updatedTask.setStatus(request.getStatus());
        return taskRepository.save(updatedTask);
    }

    @Override
    public Task completed(TaskCompleteDTO request)
    {
        Task completedTask = this.getOne(request.getId());
//        completedTask.setStatus(true);
        completedTask.setStatus("true");
        if (completedTask.getDueDate() != null
                && request.getCompleted_at() != null
//                && completedTask.getNotes() == null
        );
//    public Task completed(TaskCompleteDTO request) {
//        // Mengonversi id menjadi String jika perlu
//        String taskId = String.valueOf(request.getId());
//
//        Task completedTask = this.getOne(taskId);
//
//        // Mengubah status menjadi String "true"
//        completedTask.setStatus("true");
//
//        if (completedTask.getDueDate() != null && request.getCompleted_at() != null) {
//            // Lakukan operasi tambahan jika diperlukan ketika dueDate dan completed_at tidak null
//        }
//        {
//            if (completedTask.getDueDate().getTime() > request.getCompleted_at().getTime()) {
////                completedTask.setNotes("YOU'VE DONE YOUR TASK BEFORE DEADLINE, GOOD JOB!")
//                ;
//            }
//            else completedTask.setNotes("YOU MISS THE DEADLINE, BE BETTER NEXT TIME!");
//        }
        return taskRepository.save(completedTask);
    }

    @Override
    public void delete(Integer id) {
//    public void delete(String id) {
        taskRepository.deleteById(id);
    }
}
