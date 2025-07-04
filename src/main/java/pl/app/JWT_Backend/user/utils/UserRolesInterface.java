package pl.app.JWT_Backend.user.utils;

import pl.app.JWT_Backend.user.enums.UserRole;
import pl.app.JWT_Backend.user.models.Role;

import java.util.ArrayList;
import java.util.List;

public interface UserRolesInterface {
    default List<UserRole> getListOfUserRoles(Role role){
        List<UserRole> userRoleList = new ArrayList<UserRole>();
        switch (role.getName()) {
            case ADMIN:
                userRoleList.add(UserRole.ADMIN);
                userRoleList.add(UserRole.MANAGER);
                userRoleList.add(UserRole.IT);
                userRoleList.add(UserRole.EMPLOYEE);
                break;
            case IT:
                userRoleList.add(UserRole.IT);
                userRoleList.add(UserRole.MANAGER);
                userRoleList.add(UserRole.EMPLOYEE);
                break;
            case MANAGER:
                userRoleList.add(UserRole.MANAGER);
                userRoleList.add(UserRole.EMPLOYEE);
                break;
            case EMPLOYEE:
                userRoleList.add(UserRole.EMPLOYEE);
                break;
            default:
                userRoleList.add(UserRole.NONE);
        }
        return  userRoleList;
    }
}
