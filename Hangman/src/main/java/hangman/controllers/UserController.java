package hangman.controllers;

import java.util.ArrayList;

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
import hangmanjpa.entities.Word;

@RestController
public class UserController {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private GameDAO gameDAO;
	@Autowired
	private SecretQuestionDAO qDAO;

	
	
	
	@RequestMapping(path = "updatePassword.do", method = RequestMethod.POST)
	public ModelAndView updatePassword(String oldPassword, String newPassword, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = populateModel(session);
		
		if (user != null) {
			if (user.getPassword().equals(oldPassword)) {
				user.setPassword(newPassword);
				user = userDAO.update(user);
				session.setAttribute("user", user);
				mv.addObject("user", user);
			}
		}
		
		return mv;
	}
	
	@RequestMapping(path = "resetAccount.do", method = RequestMethod.GET)
	public ModelAndView resetAccount(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView("index");
		
		if (user != null) {
			user.setTotalPoints(0);
			gameDAO.deleteUserGames(user);
			user = userDAO.update(user);
			session.setAttribute("user", user);
			mv = populateModel(session);
		}
		
		return mv;
	}

	@RequestMapping(path = "updatePreferences.do", method = RequestMethod.GET)
	public ModelAndView updatePreferences(String preferredModelColor, String preferredDifficulty, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = populateModel(session);

		if (user != null) {
			user.setPreferredDifficulty(preferredDifficulty);
			user.setPreferredModelColor(preferredModelColor);
			user = userDAO.update(user);
			session.setAttribute("user", user);
			mv.addObject("user", user);
		}

		return mv;
	}
	
	private ModelAndView populateModel(HttpSession session) {
		ModelAndView mv = new ModelAndView("index");

		if (session != null) {
			mv.addObject("questions", qDAO.getAllSecretQuestions());
			mv.addObject("difficulty", session.getAttribute("difficulty"));

			if (session.getAttribute("playing") != null && (boolean) session.getAttribute("playing") != false) {
				mv.addObject("wordString", GameController.maskWord(((Word) session.getAttribute("word")).getWord(),
						(ArrayList<String>) session.getAttribute("guesses")));
				mv.addObject("guesses", (ArrayList<String>) session.getAttribute("guesses"));
				mv.addObject("guessesRemaining", session.getAttribute("guessesRemaining"));
				mv.addObject("hintsAvailable", session.getAttribute("hintsAvailable"));
				mv.addObject("hintsPurchased", session.getAttribute("hintsPurchased"));
				mv.addObject("character", session.getAttribute("character"));
			}

			if (session.getAttribute("user") != null) {
				User user = (User) session.getAttribute("user");
				mv.addObject("user", user);
				mv.addObject("history", gameDAO.getLastFiveGamesByUserId(user.getId()));
			}
		}

		mv.addObject("leaders", userDAO.getLeadersByPoints());
		mv.addObject("leadersLast24", gameDAO.getLeadersLastDay());

		return mv;
	}

}
