package telran.b7a;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.b7a.accounting.dao.UserAccountingRepository;
import telran.b7a.accounting.model.UserAccount;

@SpringBootApplication
public class ForumServiceSpringSecurityApplication implements CommandLineRunner{
	
	@Autowired
	UserAccountingRepository repository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ForumServiceSpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(!repository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			UserAccount adminAccount = new UserAccount("admin", password, "", "", LocalDate.now());
			adminAccount.addRole("USER".toUpperCase());
			adminAccount.addRole("MODERATOR".toUpperCase());
			adminAccount.addRole("ADMINISTRATOR".toUpperCase());
			repository.save(adminAccount);
		}
	}
}
