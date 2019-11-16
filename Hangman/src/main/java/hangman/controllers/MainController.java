package hangman.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.UserDAO;

@RestController
public class MainController {
	@Autowired
	private UserDAO user;
	
	@RequestMapping(path = {"/", "home.do"}, method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("users", user.getAllUsers());
		System.out.println(user.getAllUsers());
		return mv;
	}
	
}
