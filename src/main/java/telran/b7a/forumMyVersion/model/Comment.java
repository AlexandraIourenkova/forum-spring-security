package telran.b7a.forumMyVersion.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"user", "dateCreated" })

public class Comment {
	String user;
	@Setter
	String message;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime dateCreated = LocalDateTime.now();
	Integer likes = 0;

	public Comment(String user, String message) {
		this.user = user;
		this.message = message;
	}

	public void addLike() {
		likes++;
	}
}
