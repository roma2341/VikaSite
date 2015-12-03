package labs;

import java.util.ArrayList;

import org.neo4j.cypher.internal.compiler.v2_1.planner.logical.findShortestPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import labs.models.Post;
import labs.models.User;
import labs.services.UsersService;

@Controller
@RequestMapping("/json")
public class JSONController {
	@Autowired
	private UsersService usersService;
	
	@JsonView(View.Summary.class)
	@RequestMapping(value = "/getallposts",method = RequestMethod.GET)
	public @ResponseBody ArrayList<Post> getAllPostsJSON(){
return (ArrayList<Post>) User.getCurrentUser().getPosts();

}
	
}
