package telran.b7a.forumMyVersion.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.b7a.forumMyVersion.model.Post;


public interface ForumMongoRepository extends MongoRepository<Post, String> {
	
	Stream<Post> findByAuthor(String author);
	
	@Query("{ tags: { $in: ?0 } }")
	Stream<Post> findPostsByTags(List<String> tags);
	
	@Query("{dateCreated: {$gte:?0, $lte:?1}}")
	Stream<Post> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo);
		
}
