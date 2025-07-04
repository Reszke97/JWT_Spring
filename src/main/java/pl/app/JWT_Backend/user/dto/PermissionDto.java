package pl.app.JWT_Backend.user.dto;

import lombok.Data;

@Data
public class PermissionDto {
    private Long id;
    private String name;
    private String descriptionInPolish;
    private String descriptionInEnglish;
}
