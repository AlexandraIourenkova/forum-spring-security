package telran.b7a.forumMyVersion.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.forumMyVersion.dto.CommentDto;
import telran.b7a.forumMyVersion.dto.ContentDto;
import telran.b7a.forumMyVersion.dto.DateTimeDto;
import telran.b7a.forumMyVersion.dto.PostDto;
import telran.b7a.forumMyVersion.service.ForumService;

@RestController
public class ForumController {
	ForumService forumService;
	
	@Autowired
	public ForumController(ForumService forumService) {
		this.forumService = forumService;
	}
	@PostMapping("/forum/post/{author}")
	public ContentDto addNewPost(@RequestBody PostDto postDto, @PathVariable String author) {
		return forumService.addPost(postDto, author);
	}

	@GetMapping("/forum/post/{PostId}")
	public ContentDto findPostById(@PathVariable String PostId) {
		return forumService.findPostById(PostId);
	}

	@PutMapping("/forum/post/{PostId}/like")
	public void addLike(@PathVariable String PostId) {
		forumService.addLike(PostId);
	}
	@GetMapping("/forum/posts/author/{author}")
	public List<ContentDto> findPostByAuthor(@PathVariable String author) {
		return forumService.findPostByAuthor(author);
	}
	@PutMapping("/forum/post/{PostId}/comment/{author}")
	public ContentDto addComment(@RequestBody CommentDto commentDto, @PathVariable String author, @PathVariable String PostId) {
		return forumService.addComment(PostId, author, commentDto);
	}
	@DeleteMapping("/forum/post/{PostId}")
	public ContentDto deletePost(@PathVariable String PostId) {
		return forumService.deletePost(PostId);
	}
	@PostMapping("/forum/posts/tags")
	public List<ContentDto> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostsByTags(tags);
		
	}
	@PostMapping("/forum/posts/period")
	public List<ContentDto> findPostByPeriod(@RequestBody DateTimeDto dates) {
		return forumService.findPostByPeriod(dates.getDateFrom(), dates.getDateTo());
	}
	@PutMapping("/forum/post/{PostId}")
	public ContentDto updatePost(@PathVariable String PostId,@RequestBody PostDto postDto) {
		return forumService.updatePost(PostId, postDto);
	}


}
