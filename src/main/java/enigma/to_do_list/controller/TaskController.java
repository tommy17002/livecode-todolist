package enigma.to_do_list.controller;

import enigma.to_do_list.model.Task;
import enigma.to_do_list.service.TaskService;
import enigma.to_do_list.utils.DTO.TaskCompleteDTO;
import enigma.to_do_list.utils.DTO.TaskDTO;
import enigma.to_do_list.utils.PageResponse;
import enigma.to_do_list.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/todo/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TaskDTO request) {
        return Response.renderJson(
                taskService.create(request),
                "Success created category!",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable,
                                    @RequestParam(required = false) Boolean completed,
                                    @RequestParam(required = false) Date due_date) {
        Page<Task> task = taskService.getAll(pageable, completed, due_date);
        PageResponse<Task> result = new PageResponse<>(task);
        return Response.renderJson(
                result,
                "SHOW ALL TASKS",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                taskService.getOne(id),
                "FOUND TASK BY ID",
                HttpStatus.FOUND
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody TaskDTO request,
                                    @PathVariable Integer id) {
        return Response.renderJson(
                taskService.update(id, request),
                "TASK UPDATED",
                HttpStatus.OK
        );
    }

    @PutMapping("/completed")
    public ResponseEntity<?> completed(@RequestBody TaskCompleteDTO request) {
        return Response.renderJson(
                taskService.completed(request),
                "TASK COMPLETED",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        taskService.delete(id);
        return Response.renderJson(
                "TASK DELETED",
                HttpStatus.OK
        );
    }
}
