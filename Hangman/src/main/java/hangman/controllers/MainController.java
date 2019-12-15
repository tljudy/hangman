package hangman.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.GameDAO;
import hangman.data.SecretQuestionDAO;
import hangman.data.UserDAO;
import hangmanjpa.entities.User;

@RestController
public class MainController {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private GameDAO gameDAO;
	@Autowired
	private SecretQuestionDAO qDAO;

	@RequestMapping(path = { "/", "home.do" }, method = RequestMethod.GET)
	public ModelAndView index(HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("questions", qDAO.getAllSecretQuestions());
		mv.addObject("leaders", userDAO.getLeadersByPoints());
		mv.addObject("leadersLast24", gameDAO.getLeadersLastDay());
		User user = (User) session.getAttribute("user");

		if (user != null) {
			mv.addObject("history", gameDAO.getLastFiveGamesByUserId(user.getId()));
		}
		return mv;
	}

	@RequestMapping(path = "about", method = RequestMethod.GET)
	public ModelAndView about() {
		return new ModelAndView("about");
	}
}
