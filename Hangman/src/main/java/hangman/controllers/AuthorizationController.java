package hangman.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.UserDAO;
import hangman.data.UserDTO;
import hangmanjpa.entities.User;

@RestController
public class AuthorizationController {

	@Autowired
	private UserDAO dao;

	@RequestMapping(path = { "Login.do" }, method = RequestMethod.POST)
	public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		User user = dao.findUserByUsername(username);
		String loginFail = "Invalid username and/or password";

		try {
			if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				session.setAttribute("user", user);
			} else {
				// send login fail message
				mv.addObject("loginFail", loginFail);
			}
		} catch (NullPointerException e) {
			// send login fail message
			mv.addObject("loginFail", loginFail);
		}
		return mv;
	}

	@RequestMapping(path = { "CreateAccount.do" }, method = RequestMethod.POST)
	public ModelAndView createUser(UserDTO userDTO, HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		User user = dao.findUserByUsername(userDTO.getUsername());

		if (user != null || userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getAnswer1() == null) {
			return null;
		}

		user = dao.addUser(userDTO);
		mv.addObject("user", user);
		session.setAttribute("user", user);
		

		return mv;
	}

	@RequestMapping(path = { "logout.do" }, method = RequestMethod.GET)
	public ModelAndView userDisplay(HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:home.do");
		session.invalidate();
		return mv;
	}
}