package pl.app.JWT_Backend.user.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import pl.app.JWT_Backend.user.dto.PermissionDto;
import pl.app.JWT_Backend.user.models.Permission;
import pl.app.JWT_Backend.user.repositories.PermissionRepository;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions() {
        return this.permissionRepository.findAll();
    }

    public Permission updatePermission(PermissionDto permissionDto) {
        Optional<Permission> permission = permissionRepository.findById(permissionDto.getId());
        LocalDateTime now = LocalDateTime.now();

        return permission.map(p -> {
            p.setName(permissionDto.getName());
            p.setDescriptionInPolish(permissionDto.getDescriptionInPolish());
            p.setDescriptionInEnglish(permissionDto.getDescriptionInEnglish());
            p.setUpdatedAt(now);
            return permissionRepository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + permissionDto.getId()));
    }

    public Permission createPermission(PermissionDto permissionDto) {
        Permission newPermission = new Permission();
        LocalDateTime now = LocalDateTime.now();
        newPermission.setName(permissionDto.getName());
        newPermission.setDescriptionInEnglish(permissionDto.getDescriptionInEnglish());
        newPermission.setDescriptionInPolish(permissionDto.getDescriptionInPolish());
        newPermission.setUpdatedAt(now);
        newPermission.setCreatedAt(now);
        return permissionRepository.save(newPermission);
    }
}
