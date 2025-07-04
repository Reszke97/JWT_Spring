package pl.app.JWT_Backend.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.JWT_Backend.user.dto.UserPermissionGroupPostDto;
import pl.app.JWT_Backend.user.models.AppUser;
import pl.app.JWT_Backend.user.models.PermissionGroup;
import pl.app.JWT_Backend.user.models.UserPermissionGroup;
import pl.app.JWT_Backend.user.repositories.AppUserRepository;
import pl.app.JWT_Backend.user.repositories.PermissionGroupRepository;
import pl.app.JWT_Backend.user.repositories.UserPermissionGroupRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserPermissionGroupService {
    private final UserPermissionGroupRepository userPermissionGroupRepository;
    private final PermissionGroupRepository permissionGroupRepository;
    private final AppUserRepository appUserRepository;

    public UserPermissionGroupPostDto updateUserPermissionGroups(UserPermissionGroupPostDto userPermissionGroupPostDto){
        AppUser appUser = appUserRepository.findById(userPermissionGroupPostDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userPermissionGroupPostDto.getUserId()));
        userPermissionGroupPostDto.getPermissionGroupIds().forEach(id -> {
            LocalDateTime now = LocalDateTime.now();
            PermissionGroup permissionGroup = permissionGroupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Permission group not found for ID: " + id));
            UserPermissionGroup userPermissionGroup = new UserPermissionGroup();
            userPermissionGroup.setPermissionGroup(permissionGroup);
            userPermissionGroup.setAppUser(appUser);
            userPermissionGroup.setCreatedAt(now);
            userPermissionGroup.setUpdatedAt(now);
            userPermissionGroupRepository.save(userPermissionGroup);
        });
        return userPermissionGroupPostDto;
    }
}
