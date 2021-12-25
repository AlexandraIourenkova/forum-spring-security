package telran.b7a.forumMyVersion.service;

import java.time.LocalDate;
import java.util.List;

import telran.b7a.forumMyVersion.dto.CommentDto;
import telran.b7a.forumMyVersion.dto.ContentDto;
import telran.b7a.forumMyVersion.dto.PostDto;

public interface ForumService {
	
ContentDto addPost (PostDto postDto, String author);

ContentDto findPostById(String PostId);

void addLike(String PostId);

List <ContentDto> findPostByAuthor(String author);

ContentDto addComment(String PostId, String author, CommentDto commentDto);

ContentDto deletePost(String PostId);

List<ContentDto> findPostsByTags(List<String> tags);

List<ContentDto> findPostByPeriod(LocalDate dateFrom, LocalDate dateTo);

ContentDto updatePost(String PostId, PostDto postDto);

}
