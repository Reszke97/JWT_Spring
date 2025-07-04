package pl.app.JWT_Backend.user.controllers;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pl.app.JWT_Backend.security.JwtGenerator;
import pl.app.JWT_Backend.user.dto.AuthResponseDto;
import pl.app.JWT_Backend.user.dto.LoginDto;
import pl.app.JWT_Backend.user.dto.RefreshTokenDto;
import pl.app.JWT_Backend.user.dto.RegisterDto;

import pl.app.JWT_Backend.user.models.Department;
import pl.app.JWT_Backend.user.models.AppUser;
import pl.app.JWT_Backend.user.models.Role;
import pl.app.JWT_Backend.user.models.UserPermissionGroup;
import pl.app.JWT_Backend.user.repositories.DepartmentRepository;
import pl.app.JWT_Backend.user.repositories.AppUserRepository;
import pl.app.JWT_Backend.user.repositories.RoleRepository;
import pl.app.JWT_Backend.user.repositories.UserPermissionGroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserPermissionGroupRepository userPermissionGroupRepository;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        Long roleId = Long.valueOf(registerDto.getRoleId());
        Long departmentId = Long.valueOf(registerDto.getDepartmentId());

        Optional<Role> role = this.roleRepository.findById(roleId);
        Optional<Department> department = this.departmentRepository.findById(departmentId);

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        if (role.isEmpty()) {
            return new ResponseEntity<>("Role not found!", HttpStatus.BAD_REQUEST);
        }

        if (department.isEmpty()) {
            return new ResponseEntity<>("Department not found!", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = new AppUser();
        appUser.setRole(role.get());
        appUser.setTheme(registerDto.getTheme());
        appUser.setUsername(registerDto.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        appUser.setEmail(registerDto.getEmail());
        appUser.setSurname(registerDto.getSurname());
        appUser.setName(registerDto.getName());
        appUser.setDepartment(department.get());
        userRepository.save(appUser);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        // Authenticate user credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Retrieve user and associated permission groups
        AppUser user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<UserPermissionGroup> permissionGroups = userPermissionGroupRepository.findByAppUserId(user.getId());
        List<Long> permissionGroupIds = permissionGroups.stream()
                .map(group -> group.getPermissionGroup().getId())
                .toList();

        // Generate JWT tokens using the permission group IDs
        AuthResponseDto authResponseDto = jwtGenerator.generateTokens(user.getUsername(), permissionGroupIds);

        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken,
                                    @RequestHeader("Refresh") String refreshToken) {
        String actualAccessToken = accessToken.replace("Bearer ", "");
        String actualRefreshToken = refreshToken.replace("Bearer ", "");
        jwtGenerator.invalidateToken(actualAccessToken);
        jwtGenerator.invalidateToken(actualRefreshToken);
        return ResponseEntity.ok("Successfully logged out");
    }

    @PostMapping("refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        try {
            if (StringUtils.hasText(refreshToken) && jwtGenerator.validateToken(refreshToken)) {
                // Directly use the existing method to get the username
                String username = jwtGenerator.getUsernameFromJWT(refreshToken);
                List<Long> permissionGroupIds = jwtGenerator.extractPermissionGroups(refreshToken);

                // Generate new access and refresh tokens with updated permission groups
                String newAccessToken = jwtGenerator.generateAccessToken(username, permissionGroupIds);
                String newRefreshToken = jwtGenerator.generateRefreshToken(username, permissionGroupIds);

                // Invalidate the old refresh token
                jwtGenerator.invalidateToken(refreshToken);

                return ResponseEntity.ok(new AuthResponseDto(newAccessToken, newRefreshToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDto("Invalid refresh token"));
            }
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDto("Invalid refresh token"));
        }
    }
}