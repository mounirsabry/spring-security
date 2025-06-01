package jets.spring_security;

import jets.spring_security.model.entity.User;
import jets.spring_security.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepo userRepo) {
		return _ -> {
			List<User> users = new ArrayList<>();
			users.add(new User("user1", "user1abc", List.of("USER")));
			users.add(new User("user2", "user2abc", List.of("USER", "ADMIN")));

			for (User user : users) {
				userRepo.save(user);
			}
		};
	}
}
