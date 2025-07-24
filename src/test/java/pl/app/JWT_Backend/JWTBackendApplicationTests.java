package pl.app.JWT_Backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.app.JWT_Backend.user.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTBackendApplicationTests {

	@Autowired
	private DepartmentService departmentService;

	@Test
	void contextLoads() {
		assertNotNull(departmentService);
	}
}
