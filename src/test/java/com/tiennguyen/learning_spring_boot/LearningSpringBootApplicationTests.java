package com.tiennguyen.learning_spring_boot;

import com.tiennguyen.learning_spring_boot.clientproxy.UserResourceV1;
import com.tiennguyen.learning_spring_boot.model.User;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LearningSpringBootApplicationTests {
	/*@Autowired
private UserResourceV1 userResourceV1;
	@Test
	void itShouldFetchAllUsers() {
		List<User> users = userResourceV1.fetchUsers(null);
		assertThat(users).hasSize(1);

		User joe = new User(null, "Joe", "Jones", User.Gender.MALE, 22, "joe.jones@gmail.com");
		assertThat(users.get(0)).isEqualToIgnoringGivenFields(joe, "userUid");
		assertThat(users.get(0).getUserUid()).isInstanceOf(UUID.class);
		assertThat(users.get(0).getUserUid()).isNotNull();

	}

	@Test
	public void shouldInsertUser() throws Exception {
		//GIVEN
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Joe", "Jones", User.Gender.MALE, 22, "joe.jones@gmail.com");
		//When
		userResourceV1.insertNewUser(user);
		//THEN
		User joe = userResourceV1.fetchUser(userUid);
		assertThat(joe).isEqualToComparingFieldByField(user);
	}*/

}
