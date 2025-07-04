package pl.app.JWT_Backend.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserPermissionGroupPostDto {
    private Long userId;
    private List<Long> permissionGroupIds;
}
