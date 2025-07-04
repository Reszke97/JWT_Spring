package pl.app.JWT_Backend.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.app.JWT_Backend.user.models.AppUser;
import pl.app.JWT_Backend.user.repositories.AppUserRepository;
import pl.app.JWT_Backend.user.repositories.PermissionAssignedToGroupRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final PermissionAssignedToGroupRepository permissionAssignedToGroupRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new User(appUser.getUsername(), appUser.getPassword(), List.of());
    }


    /**
     * Retrieves permissions associated with the given permission group IDs.
     *
     * @param permissionGroupIds List of permission group IDs.
     * @return A list of permission names derived from the associated permission groups.
     */
    public List<String> getPermissionsFromGroupIds(List<Long> permissionGroupIds) {
        if (permissionGroupIds == null || permissionGroupIds.isEmpty()) {
            return List.of(); // Return an empty list if permissionGroupIds is null or empty
        }

        Set<String> permissions = permissionAssignedToGroupRepository.findByPermissionGroupIdIn(permissionGroupIds)
                .stream()
                .map(assignedPermission -> assignedPermission.getPermission().getName())
                .collect(Collectors.toSet());

        return List.copyOf(permissions);
    }
}