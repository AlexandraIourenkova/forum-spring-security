package telran.b7a.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NonNull
public class RegisterDto {
	String login;
    String password;
    String firstName;
    String lastName;

}
