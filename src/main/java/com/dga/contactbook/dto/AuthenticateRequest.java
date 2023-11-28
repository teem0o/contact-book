package com.dga.contactbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateRequest {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, message = "Username must have at least 2 character")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, message = "Password must have at least 3 character")
    private String password;

}
