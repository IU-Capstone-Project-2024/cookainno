// SaveUserInfoRequest.java
package org.innopolis.cookainno.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaveUserInfoRequest {

    private Long userId;

    @Min(0)
    private Integer height;

    @Min(0)
    private Integer weight;

    private LocalDate dateOfBirth;
}
