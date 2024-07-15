package enigma.to_do_list.utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor

public class TaskCompleteDTO {
    @NotBlank(message = "Write your task ID that have been completed!")
    private Integer id;

    private Boolean completed;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completed_at;
}
