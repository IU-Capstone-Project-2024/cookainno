package org.innopolis.cookainno.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Email confirmation request")
public class ConfirmEmailRequest {

    @Schema(description = "Email address", example = "vladick@yandex.ru")
    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "Email address must be in the format user@yandex.ru")
    private String email;

    @Schema(description = "Confirmation code", example = "1234")
    @NotBlank(message = "Confirmation code cannot be blank")
    private String confirmationCode;
}
