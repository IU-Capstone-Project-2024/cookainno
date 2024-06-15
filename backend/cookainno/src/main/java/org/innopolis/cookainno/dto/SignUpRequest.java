package org.innopolis.cookainno.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Registration request")
public class SignUpRequest {

    @Schema(description = "Username", example = "Vladick")
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(description = "Email address", example = "vladick@yandex.ru")
    @Size(min = 5, max = 255, message = "Email address must be between 5 and 255 characters")
    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "Email address must be in the format user@yandex.ru")
    private String email;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 5, max = 255, message = "Password length must be between 5 and 255 characters")
    private String password;
}
