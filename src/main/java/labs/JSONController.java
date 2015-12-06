package labs;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.cypher.internal.compiler.v2_1.planner.logical.findShortestPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import labs.models.Comment;
import labs.models.Post;
import labs.models.User;
import labs.services.PostsService;
import labs.services.UsersService;

@Controller
@RequestMapping("/json")
public class JSONController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private PostsService postsService;
	
	@JsonView(View.Summary.class)
	@RequestMapping(value = "/getuser_json",method = RequestMethod.POST)
	public @ResponseBody ArrayList<Post> getAllPostsJSON(){
return (ArrayList<Post>) User.getCurrentUser().getPosts();

}
	
	@JsonView(View.Summary.class)
	@RequestMapping(value = "/get_comments",method = RequestMethod.POST)
	public @ResponseBody List<Comment> getCommentsJSON(@RequestBody Long postId){
return postsService.getPost(postId).getComments();
}
	
}
