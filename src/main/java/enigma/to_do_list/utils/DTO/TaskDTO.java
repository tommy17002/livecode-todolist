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
//    private String user_id;

//    @NotNull (message = "Category ID cannot be null!")
//    private Integer category_id;

    @NotBlank(message = "Write your title task name!")
    private String title;

    @NotBlank(message = "Write your title description!")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    private Date dueDate;
    private String dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createdAt;
//    private String createdAt;

//    private Boolean status;
    private String status;

}
