package enigma.to_do_list.controller;

import enigma.to_do_list.model.Category;
import enigma.to_do_list.service.CategoryService;
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
@RequestMapping("/api/todo/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Category request) {
        return Response.renderJson(
                categoryService.create(request),
                "Success created category!",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable,
                                    @RequestParam(required = false) String name ) {
        Page<Category> cat = categoryService.getAll(pageable, name);
        PageResponse<Category> result = new PageResponse<>(cat);
        return Response.renderJson(
                result,
                "SHOW ALL CATEGORIES",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                categoryService.getOne(id),
                "FOUND CATEGORY BY ID",
                HttpStatus.FOUND
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Category request,
                                    @PathVariable Integer id) {
        return Response.renderJson(
                categoryService.update(request, id),
                "CATEGORY UPDATED",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return Response.renderJson(
                "CATEGORY DELETED",
                HttpStatus.OK
        );
    }
}
