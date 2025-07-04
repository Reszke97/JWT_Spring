package pl.app.JWT_Backend.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.JWT_Backend.user.dto.JsonResponse;
import pl.app.JWT_Backend.user.dto.PermissionsAssignedToGroupDto;
import pl.app.JWT_Backend.user.dto.PermissionsAssignedToGroupWithoutObjectsDto;
import pl.app.JWT_Backend.user.services.PermissionAssignedToGroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/permission-assigned-to-group")
@RequiredArgsConstructor
public class PermissionAssignedToGroupController {

    private final PermissionAssignedToGroupService permissionAssignedToGroupService;

    @GetMapping
    public List<PermissionsAssignedToGroupDto> getPermissionsAssignedToGroup(@RequestParam(value = "permissionGroupId", required = true) Long permissionGroupId){
        return this.permissionAssignedToGroupService.getPermissionsAssignedToGroup(permissionGroupId);
    }

    @PostMapping("post")
    public ResponseEntity<List<PermissionsAssignedToGroupDto>> updatePermissionsAssignedToGroup(@RequestBody PermissionsAssignedToGroupWithoutObjectsDto permissionsAssignedToGroupWithoutObjectsDto) {
        List<PermissionsAssignedToGroupDto> newPermissionGroupDto = permissionAssignedToGroupService.savePermissionsAssignedToGroup(permissionsAssignedToGroupWithoutObjectsDto);
        return ResponseEntity.ok(newPermissionGroupDto);
    }

    @DeleteMapping("delete")
    public ResponseEntity<JsonResponse> deletePermissionsAssignedToGroup(@RequestParam Long permissionAssignedToGroupId) {
        permissionAssignedToGroupService.deletePermissionAssignedToGroup(permissionAssignedToGroupId);
        JsonResponse jsonRes = new JsonResponse();
        jsonRes.setMessage("Permission assigned to group with id: " + permissionAssignedToGroupId + "successfully deleted.");
        jsonRes.setStatus(200);
        return ResponseEntity.ok(jsonRes);
    }
}
