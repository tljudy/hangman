package hangman.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.UserDAO;
import hangman.data.UserDTO;
import hangmanjpa.entities.User;
import hangmanjpa.entities.UserSecretQuestion;

@RestController
public class AuthorizationController {

	@Autowired
	private UserDAO dao;

	@RequestMapping(path = { "Login.do" }, params = { "username", "password" }, method = RequestMethod.POST)
	public ModelAndView login(String username, String password, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:home.do");
		User user = dao.findUserByUsername(username);

		try {
			if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				session.setAttribute("myUser", user);
			} else {
				mv.setViewName("loginFail");
			}
		} catch (NullPointerException e) {
			mv.setViewName("loginFail");
		}
		return mv;
	}

	// test
	@RequestMapping(path = { "CreateAccount.do" }, method = RequestMethod.POST)
	public ModelAndView createUser(UserDTO userDTO, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:test");
		User user = dao.findUserByUsername(userDTO.getUsername());

		if (user != null || userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getAnswer1() == null) {
			return null;
		}

		user = dao.addUser(userDTO);
		System.out.println(user);
		for (UserSecretQuestion ans: user.getUserSecretQuestions()) {
			System.out.println(ans);
		}

		return mv;
	}

	@RequestMapping(path = { "logout.do" }, method = RequestMethod.GET)
	public ModelAndView userDisplay(HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:home.do");
		session.invalidate();
		return mv;
	}
}