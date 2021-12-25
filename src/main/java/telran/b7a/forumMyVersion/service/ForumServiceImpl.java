package telran.b7a.forumMyVersion.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.b7a.forumMyVersion.dao.ForumMongoRepository;
import telran.b7a.forumMyVersion.dto.CommentDto;
import telran.b7a.forumMyVersion.dto.ContentDto;
import telran.b7a.forumMyVersion.dto.PostDto;
import telran.b7a.forumMyVersion.exeption.PostNotFoundExeption;
import telran.b7a.forumMyVersion.model.Post;
import telran.b7a.forumMyVersion.service.logging.PostLogger;
@Service
public class ForumServiceImpl implements ForumService {
	ForumMongoRepository forumRepository;
	ModelMapper modelMapper;
	static final Logger logger = LoggerFactory.getLogger(ForumServiceImpl.class);
	
	@Autowired
	public ForumServiceImpl(ForumMongoRepository forumRepository, ModelMapper modelMapper) {
		this.forumRepository = forumRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ContentDto addPost(PostDto postDto, String author) {
		Post post = modelMapper.map(postDto, Post.class);
		post.setAuthor(author);
		forumRepository.save(post);
		return modelMapper.map(post, ContentDto.class);
	}

	@Override
	public ContentDto findPostById(String postId) {
		Post post = forumRepository.findById(postId).orElseThrow(() -> new PostNotFoundExeption(postId));
		return modelMapper.map(post, ContentDto.class);
	}

	@Override
	public void addLike(String PostId) {
		Post post = forumRepository.findById(PostId).orElseThrow(() -> new PostNotFoundExeption(PostId));
		post.addLikes();
		forumRepository.save(post);
	}

	@Override
	public List<ContentDto> findPostByAuthor(String author) {
		return forumRepository.findByAuthor(author)
				.map(p -> modelMapper.map(p, ContentDto.class))
				.collect(Collectors.toList());
	}

	
	@Override
	public ContentDto deletePost(String PostId) {
		Post post = forumRepository.findById(PostId).orElseThrow(() -> new PostNotFoundExeption(PostId));
		forumRepository.delete(post);
		return modelMapper.map(post, ContentDto.class);
	}

	@Override
	public List<ContentDto> findPostsByTags(List<String> tags) {
		return forumRepository.findPostsByTags(tags)
				.map(p -> modelMapper.map(p, ContentDto.class))
				.collect(Collectors.toList());
		}

	@Override
	public List<ContentDto> findPostByPeriod(LocalDate dateFrom, LocalDate dateTo) {
			return forumRepository.findPostsByPeriod(dateFrom, dateTo)
				.map(p -> modelMapper.map(p, ContentDto.class))
				.collect(Collectors.toList());
	}
	@PostLogger
	@Override
	public ContentDto updatePost(String PostId, PostDto PostDto) {
		Post post = forumRepository.findById(PostId).orElseThrow(() -> new PostNotFoundExeption(PostId));
		String content = PostDto.getContent();
		if(content != null) {
			post.setContent(content);
		}
		String title = PostDto.getTitle();
		if(title != null) {
			post.setTitle(title);
		}
		Set<String> tags = PostDto.getTags();
		if(tags != null) {
			tags.forEach(post::addTags);
		}
		forumRepository.save(post);
		return modelMapper.map(post, ContentDto.class);
	}

	@Override
	public ContentDto addComment(String PostId, String author, CommentDto commentDto) {
		Post post = forumRepository.findById(PostId).orElseThrow(() -> new PostNotFoundExeption(PostId));
		//Comment comment = modelMapper.map(commentDto, Comment.class);
		telran.b7a.forumMyVersion.model.Comment comment = new telran.b7a.forumMyVersion.model.Comment(author, commentDto.getMessage());
		post.addComment(comment);
		forumRepository.save(post);
		return modelMapper.map(post, ContentDto.class);
	}

}
