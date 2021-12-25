package telran.b7a.accounting.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "login" })
@Document(collection = "users")
public class UserAccount {
	@Id
	String login;
	String password;
	String firstName;
	String lastName;
	Set<String> roles = new HashSet<>();
	private static long passwordExpPeriod = 2;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate passwordChangeDate = LocalDate.now().minusDays(5);
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate passwordExpDate = passwordChangeDate.plusDays(passwordExpPeriod);

	public UserAccount(String login, String password, String firstName, String lastName, LocalDate passwordChangeDate) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		//this.passwordChangeDate = LocalDate.now();
		//this.passwordExpDate = passwordChangeDate.plusDays(passwordExpPeriod);

	}

	public boolean addRole(String role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(role);
	}

	public boolean isPasswordExpired() {
		if (this.passwordExpDate.isBefore(LocalDate.now())) {
			return true;
		}
		return false;
	}

}
