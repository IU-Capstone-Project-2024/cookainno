// SaveUserInfoResponse.java
package org.innopolis.cookainno.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaveUserInfoResponse {
    private Long userId;
    private Integer height;
    private Integer weight;
    private LocalDate dateOfBirth;
}