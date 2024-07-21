package enigma.to_do_list.controller;

import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.service.UserService;
import enigma.to_do_list.utils.DTO.UserDTO;
import enigma.to_do_list.utils.PageResponse;
import enigma.to_do_list.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO request) {
        return Response.renderJson(
                userService.create(request),
                "Success created admin!",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable,
                                    @RequestParam(required = false) String name ) {
        Page<UserEntity> cat = userService.getAll(pageable, name);
        PageResponse<UserEntity> result = new PageResponse<>(cat);
        return Response.renderJson(
                result,
                "SHOW ALL USERS",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                userService.getOne(id),
                "FOUND A USER BY ID",
                HttpStatus.FOUND
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody UserDTO request,
                                    @PathVariable Integer id) {
        return Response.renderJson(
                userService.update(request, id),
                "CATEGORY UPDATED",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        userService.delete(id);
        return Response.renderJson(
                "CATEGORY DELETED",
                HttpStatus.OK
        );
    }
}
