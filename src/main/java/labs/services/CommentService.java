package labs.services;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import labs.models.Comment;
import labs.models.Post;
import labs.models.User;
import labs.repositories.CommentRepository;
import labs.repositories.PostRepository;

@Service
public class CommentService {
	@Autowired
	private PostRepository postsRepo;
	@Autowired
	private CommentRepository commentRepo;
	@Transactional
	public ArrayList<Comment> getComments(Post post) {
		ArrayList<Comment> posts = commentRepo.findByPost(post);
		return posts; // spring рахує сторінки з нуля
		
	}
	@Transactional
	public void addComment(Long postId,String text) {
		User currentUser = User.getCurrentUser();
		Post post = postsRepo.findOne(postId);
		if (text.length()>0)
		commentRepo.save(new Comment(currentUser,post, text));
	}
	@Transactional
	public void removeComment(Long commentId) {
		User currentUser = User.getCurrentUser();
		if(commentRepo.findOne(commentId).getAuthor().getId()==currentUser.getId())
		commentRepo.delete(commentId);
	}
	
}
