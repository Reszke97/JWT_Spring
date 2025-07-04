package pl.app.JWT_Backend.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.JWT_Backend.user.dto.PermissionGroupDto;
import pl.app.JWT_Backend.user.models.PermissionGroup;
import pl.app.JWT_Backend.user.services.PermissionGroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/permission-group")
@RequiredArgsConstructor
public class PermissionGroupController {
    private final PermissionGroupService permissionGroupService;

    @GetMapping
    public ResponseEntity<List<PermissionGroup>> getPermissionGroups(){
        return ResponseEntity.ok(this.permissionGroupService.getAllPermissionGroups());
    }

    @PostMapping("create")
    public ResponseEntity<PermissionGroup> createPermissionGroup(@RequestBody PermissionGroupDto permissionGroupDto){
        PermissionGroup newPermissionGroup = permissionGroupService.createPermissionGroup(permissionGroupDto);
        return ResponseEntity.ok(newPermissionGroup);
    }
}
