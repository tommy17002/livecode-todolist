package enigma.to_do_list.utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class TaskDTO {
    @NotNull (message = "User ID cannot be null!")
    private Integer user_id;

    @NotNull (message = "Category ID cannot be null!")
    private Integer category_id;

    @NotBlank(message = "Write your to do task name!")
    private String to_do;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date due_date;

    private String notes;

    private Boolean completed;
}
