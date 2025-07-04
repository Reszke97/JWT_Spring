package pl.app.JWT_Backend.user.controllers;

import java.net.URI;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.app.JWT_Backend.security.JwtGenerator;
import pl.app.JWT_Backend.user.dto.PermissionDto;
import pl.app.JWT_Backend.user.services.AppUserService;
import pl.app.JWT_Backend.user.services.PermissionService;
import pl.app.JWT_Backend.user.models.Permission;


@RestController
@RequestMapping("/api/v1/user/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    private final JwtGenerator jwtGenerator;
    private final AppUserService appUserService;

    @GetMapping
    public List<Permission> getPermissionList(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String username = jwtGenerator.getUsernameFromJWT(token);
        return this.appUserService.getAllPermissionsForUser(username);
    }

    @PatchMapping("patch")
    public ResponseEntity<Permission> updatePermission(@RequestBody PermissionDto permissionDto) {
        Permission permission = permissionService.updatePermission(permissionDto);
        return ResponseEntity.ok(permission);
    }

    @PostMapping("post")
    public ResponseEntity<Permission> createPermission(@RequestBody PermissionDto permissionDto) {
        Permission permission = permissionService.createPermission(permissionDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(permission.getId())
                .toUri();
        return ResponseEntity.created(location).body(permission);
    }
}
