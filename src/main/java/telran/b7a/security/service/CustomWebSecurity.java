package telran.b7a.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.UserAccountingRepository;
import telran.b7a.accounting.exeption.UserNotFoundException;
import telran.b7a.accounting.model.UserAccount;
import telran.b7a.forumMyVersion.dao.ForumMongoRepository;
import telran.b7a.forumMyVersion.model.Post;

@Service("customSecurity")
public class CustomWebSecurity {
	ForumMongoRepository repository;
	UserAccountingRepository userRepository;
	@Autowired
	public CustomWebSecurity(ForumMongoRepository repository, UserAccountingRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	}

	public boolean checkPostAuthority(String postId, String userName) {
		Post post = repository.findById(postId).orElse(null);
		return post == null || userName.equals(post.getAuthor());
		
	}
	
	public boolean checkPasswordExpDate(String userName) {
		if(userName == null || userName.isEmpty()|| userName.equalsIgnoreCase("anonymousUser")) {
			return true;
		}
		UserAccount userAccount = userRepository.findById(userName).orElseThrow(() -> new UserNotFoundException());
		return userAccount.isPasswordExpired();
	}

}
