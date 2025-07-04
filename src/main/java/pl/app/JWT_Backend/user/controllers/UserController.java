package pl.app.JWT_Backend.user.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.JWT_Backend.user.dto.UserPermissionGroupPostDto;
import pl.app.JWT_Backend.user.dto.UserWithPermissionsDto;
import pl.app.JWT_Backend.user.services.AppUserService;
import pl.app.JWT_Backend.user.services.UserPermissionGroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final AppUserService appUserService;
    private final UserPermissionGroupService userPermissionGroupService;

    @GetMapping("get-all-users-with-permission-groups")
    public ResponseEntity<List<UserWithPermissionsDto>> getUsersWithPermissionGroups() {
        return ResponseEntity.ok(this.appUserService.getUsersWithPermissionGroups());
    }

    @PostMapping("update/permission-groups")
    public ResponseEntity<UserPermissionGroupPostDto> updateUserPermissionGroups(@RequestBody UserPermissionGroupPostDto userPermissionGroupPostDto) {
        return ResponseEntity.ok(this.userPermissionGroupService.updateUserPermissionGroups(userPermissionGroupPostDto));
    }
}
