package telran.b7a.forumMyVersion.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor

public class Post {
	@Id
	String id;
	@Setter
	String title;
	@Setter
	String content;
	@Setter
	String author;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime dateCreated = LocalDateTime.now();
	@Setter
	Set<String> tags = new HashSet<String>();
	@Setter
	Integer likes = 0;
	@Setter
	List<Comment> comments = new ArrayList<Comment>();
	
	
	public Post(String title, String content, String author, Set<String> tags) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.tags = tags;
//		dateCreated = LocalDateTime.now();
//		likes = 0;
		
	}
	
	public Integer addLikes() {
		return likes++;
		
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public void addTags(String tag) {
		tags.add(tag);
	}
	
}
