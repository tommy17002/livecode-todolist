package enigma.to_do_list.service;


import enigma.to_do_list.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;

public interface CategoryService {
    Category create(Category request);
    Page<Category> getAll(Pageable pageable, String name);
    Category getOne(Integer id);
    Category update(Category request, Integer id);
    void delete(Integer id);
}
