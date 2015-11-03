package labs;

import labs.models.Post;
import labs.models.User;
import labs.services.PostsService;
import labs.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String userpage(Model model) {
		model.addAttribute("user",User.getCurrentUser());
		return "user_page";
	}
	
	
	@RequestMapping("/list")
	public String list(Model model, 
					   @RequestParam(value = "page", defaultValue = "1") int page) {
		System.out.println("before get posts");
		Page<Post> postsPage = postsService.getPosts(page, 5); // 5 постів на сторінку
		model.addAttribute("posts", postsPage.getContent());
		model.addAttribute("pagesCount", postsPage.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("currentUser", User.getCurrentUser());
		return "list";
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String createPost(@RequestParam("message") String postText) {
		postsService.addPost(postText);
		return "redirect:/list";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		if (!User.isAnonymous())
		return "redirect:/list";
		return "redirect:/";
	}
	
}
