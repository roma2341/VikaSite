package labs.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import labs.models.Post;
import labs.models.User;
import labs.repositories.PostRepository;
import labs.repositories.UserRepository;

@Service
public class PostsService {
	@Autowired
	private UserRepository usersRepo;
	
	@Autowired
	private PostRepository postsRepo;

	@Transactional
	public Page<Post> getPosts(int page, int pageSize) {
		User currentUser = usersRepo.findOne(User.getCurrentUserId());
		System.out.println("current user id:"+currentUser.getId());
		return postsRepo.findByAuthorId(currentUser.getId(),
				new PageRequest(page-1, pageSize) // spring рахує сторінки з нуля
		);
	}
	@Transactional
	public Page<Post> getAllPosts(int page, int pageSize) {
		User currentUser = usersRepo.findOne(User.getCurrentUserId());
		System.out.println("current user id:"+currentUser.getId());
		return postsRepo.findAll(new PageRequest(page-1, pageSize));
	}
	@Transactional
	   public void removePost(Long id){
		   System.out.println("removeLinkIndo:"+id);
		   Post singlePost = postsRepo.findOne(id);
		   if (User.getCurrentUserId()==singlePost.getAuthor().getId())
		postsRepo.delete(id);
	   }

	@Transactional
	public void addPost(String text) {
		User currentUser = usersRepo.findOne(User.getCurrentUserId());
		if (text.length()>0)
		postsRepo.save(new Post(currentUser, text, new Date()));
	}
}
