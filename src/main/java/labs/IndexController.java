package labs;

import labs.models.Post;
import labs.models.User;
import labs.models.User.Gender;
import labs.services.PostsService;
import labs.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.neo4j.cypher.internal.compiler.v2_1.planner.logical.findShortestPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {
	
	@Autowired
	private PostsService postsService;
	
	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/")
	public String index(Model model) {
		// якщо користувач вже увійшов, то перекинути його з реєстрації на домашню сторінку		
		if(!User.isAnonymous()) {
			return "redirect:/list";
		}
		//model.addAttribute("form", new RegistrationForm());
		return "index";
	}
	
	@RequestMapping("/userpage")
	public String starpage(Model model, 
			   @RequestParam(value = "page", defaultValue = "1") int page) {
Page<Post> postsPage = postsService.getPosts(page, 5); // 5 постів на сторінку
model.addAttribute("posts", postsPage.getContent());
model.addAttribute("pagesCount", postsPage.getTotalPages());
model.addAttribute("currentPage", page);
model.addAttribute("currentUser", User.getCurrentUser());
model.addAttribute("user",User.getCurrentUser());
return "user_page";
}
	
	
	@RequestMapping("/list")
	public String list(Model model, 
					   @RequestParam(value = "page", defaultValue = "1") int page) {
		System.out.println("before get posts");
		Page<Post> postsPage = postsService.getAllPosts(page, 5); // 5 постів на сторінку
		model.addAttribute("posts", postsPage.getContent());
		model.addAttribute("pagesCount", postsPage.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("currentUser", User.getCurrentUser());
		return "list";
	}
	@RequestMapping(value ="/postdetails",method = RequestMethod.GET)
		public String postDetails(Model model,@RequestParam("post_id") Long postId, HttpServletRequest request)	{
		Post currentPost = postsService.getPost(postId);
		model.addAttribute("post",currentPost);
		model.addAttribute("author",usersService.getUser(currentPost.getAuthor().getId()));
		return "textview";
	}
	@RequestMapping(value="/userprofile",method=RequestMethod.GET)
		public String userProfile(Model model){
			model.addAttribute("user",usersService.getUser(User.getCurrentUser().getId()));
			return "user_profile";
		}
	
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String createPost(HttpServletRequest request,@RequestParam("message") String postText) {
		postsService.addPost(postText);
		String referer = request.getHeader("Referer");
	    return "redirect:"+ referer;
	}
	@RequestMapping(value = "/storeuserinfo", method = RequestMethod.POST)
	public String storeUserInfo(HttpServletRequest request,@RequestParam(value="address",defaultValue = "") String address,
			@RequestParam(value="phone",defaultValue = "") String phone,@RequestParam(value="fullName",defaultValue = "") String fullName,
			@RequestParam(value="gender",defaultValue = "") Gender gender ) {
		User u = usersService.getUser(User.getCurrentUserId());
		u.setAddress(address);
		u.setPhone(phone);
		u.setFullName(fullName);
		u.setGender(gender);
		usersService.updateUserInfo(u);
		String referer = request.getHeader("Referer");
	    return "redirect:"+ referer;
	}
	
	
	@RequestMapping(value = "/remove_post", method = RequestMethod.GET)
	public String removePost(@RequestParam("post_id") Long postId, HttpServletRequest request) {
		postsService.removePost(postId);
		String referer = request.getHeader("Referer");
	    return "redirect:"+ referer;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(RedirectAttributes attr,HttpSession session) {
		if (!User.isAnonymous())
		return "redirect:/list";
		return "redirect:/";
	}
	
			
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String register(@RequestParam("email_submiting") String email, 
						   @RequestParam("password_submiting") String pass,HttpServletRequest request) {
		
		usersService.register(email, pass);

		return "redirect:/";
	}
	
}
