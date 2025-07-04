package pl.app.JWT_Backend.user.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
