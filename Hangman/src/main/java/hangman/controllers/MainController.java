package hangman.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.Scraper;
import hangman.data.UserDAO;

@RestController
public class MainController {
	@Autowired
	private UserDAO user;
	@Autowired
	private Scraper sc;
	
	@RequestMapping(path = {"/", "home.do"}, method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("users", user.getAllUsers());
		
		sc.Scrape("peeper");
		sc.Scrape("alive");
		sc.Scrape("dictionary");
		sc.Scrape("brontosaurus");
		
		return mv;
	}
	
}
