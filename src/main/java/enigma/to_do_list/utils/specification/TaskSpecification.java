package enigma.to_do_list.utils.specification;

import enigma.to_do_list.model.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> getSpecification(Boolean completed, Date due_date) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (completed != null) {
                predicates.add(criteriaBuilder.equal(root.get("completed"), completed));
            }

            if (due_date != null) {
                predicates.add(criteriaBuilder.equal(root.get("due_date"), due_date));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
