package telran.b7a.forumMyVersion.service.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j(topic = "Post Service")
@Service
public class ForumServiceLogging {
	@Pointcut("execution(public * telran.b7a.forumMyVersion.service.ForumServiceImpl.*(..))")
	public void bulkingFind() {

	}
	@Pointcut("execution(public * "
			+ "telran.b7a.forumMyVersion.service.ForumServiceImpl.findPostById(..)) && args(postId)")
	
	public void findPostById(String postId) {}

	@Pointcut("@annotation(PostLogger) && args(postId)")
	public void annotated(String postId) {}
	
	
	@Around("bulkingFind()")
	public Object bulkingFindLogging(ProceedingJoinPoint pjp) throws Throwable {
		// Object[] args = pjp.getArgs();
		long t1 = System.currentTimeMillis();
		Object retVal = pjp.proceed();
		long t2 = System.currentTimeMillis();
		log.info("class - {}, duration = {}", pjp.getSignature().getName(), (t2 - t1));
		return retVal;
	}
	@Before("findPostById(postId)")
	public void signInLogging(String postId) {
		log.info("user {} sign in", postId);
	}
	
	@AfterReturning("annotated(postId)")
	public void updatePostLoggin(String postId) {
		log.info("post with id {} update", postId);
	}
}
