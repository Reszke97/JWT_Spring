package pl.app.JWT_Backend.user.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import pl.app.JWT_Backend.security.JwtAuthenticationFilter;
import pl.app.JWT_Backend.security.JwtGenerator;
import pl.app.JWT_Backend.user.dto.UserWithPermissionsDto;
import pl.app.JWT_Backend.user.services.AppUserService;
import pl.app.JWT_Backend.user.services.UserPermissionGroupService;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false) //wyłącza JwtAuthenticationFilter i inne security filtry
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private UserPermissionGroupService userPermissionGroupService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtGenerator jwtGenerator;

    @Test
    void getUsersWithPermissionGroups_returnsList() throws Exception {
        UserWithPermissionsDto mockUser = new UserWithPermissionsDto();
        mockUser.setId(1L);

        when(appUserService.getUsersWithPermissionGroups())
                .thenReturn(List.of(mockUser));

        MvcResult result = mockMvc.perform(get("/api/v1/user/get-all-users-with-permission-groups"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("🟡 Response body:\n" + responseBody);
    }
}