package pl.app.JWT_Backend.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.JWT_Backend.user.dto.UserPermissionGroupDto;
import pl.app.JWT_Backend.user.dto.UserWithPermissionsDto;
import pl.app.JWT_Backend.user.models.*;
import pl.app.JWT_Backend.user.repositories.AppUserRepository;
import pl.app.JWT_Backend.user.repositories.PermissionAssignedToGroupRepository;
import pl.app.JWT_Backend.user.repositories.UserPermissionGroupRepository;

import java.io.Console;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final UserPermissionGroupRepository userPermissionGroupRepository;
    private final PermissionAssignedToGroupRepository permissionAssignedToGroupRepository;

    public List<UserWithPermissionsDto> getUsersWithPermissionGroups(){
        List<AppUser> appUser = this.appUserRepository.findAll();
        return appUser.stream().map(user -> {
            UserWithPermissionsDto userWithPermissionsDto = new UserWithPermissionsDto();

            List<UserPermissionGroupDto> userPermissionGroupDtos = user.getUserPermissionGroups().stream().map(userPerm -> {
                UserPermissionGroupDto userPermissionGroupDto = new UserPermissionGroupDto();
                userPermissionGroupDto.setId(userPerm.getId());
                userPermissionGroupDto.setPermissionGroup(userPerm.getPermissionGroup());
                userPermissionGroupDto.setCreatedAt(userPerm.getCreatedAt());
                userPermissionGroupDto.setUpdatedAt(userPerm.getUpdatedAt());
                return userPermissionGroupDto;
            }).toList();

            userWithPermissionsDto.setId(user.getId());
            userWithPermissionsDto.setName(user.getName());
            userWithPermissionsDto.setSurname(user.getSurname());
            userWithPermissionsDto.setDepartment(user.getDepartment());
            userWithPermissionsDto.setUserPermissionGroups(userPermissionGroupDtos);
            return userWithPermissionsDto;
        }).toList();
    }

    public List<Permission> getAllPermissionsForUser(String username) {
        AppUser appUser = this.appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find all UserPermissionGroups for the user
        List<UserPermissionGroup> userPermissionGroups = this.userPermissionGroupRepository.findByAppUser(appUser);

        // Collect all PermissionGroups the user belongs to
        List<PermissionGroup> permissionGroups = userPermissionGroups.stream()
                .map(UserPermissionGroup::getPermissionGroup)
                .toList();

        // Get all Permissions associated with the user's PermissionGroups
        return permissionGroups.stream()
                .flatMap(group -> this.permissionAssignedToGroupRepository.findByPermissionGroup(group).stream())
                .map(PermissionAssignedToGroup::getPermission)
                .distinct()
                .collect(Collectors.toList());
    }

}
