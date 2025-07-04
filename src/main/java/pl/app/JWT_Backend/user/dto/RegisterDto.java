package pl.app.JWT_Backend.user.dto;

import lombok.Data;
import pl.app.JWT_Backend.user.enums.Theme;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private Integer roleId;
    private String name;
    private String surname;
    private Theme theme;
    private String email;
    private Integer departmentId;
}
