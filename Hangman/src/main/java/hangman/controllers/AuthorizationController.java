package hangman.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.UserDAO;
import hangmanjpa.entities.User;

@RestController
public class AuthorizationController {

	@Autowired
	private UserDAO dao;

	@RequestMapping(path = { "authorize.do" }, params = { "username", "password" }, method = RequestMethod.POST)
	public ModelAndView checkUser(String username, String password, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:home.do");
		User user = dao.findUserByUsername(username);

		try {
			if (username.contentEquals(user.getUsername()) && password.equals(user.getPassword()) ) {
				session.setAttribute("myUser", user);
			}else {
				mv.setViewName("loginFail");
			}
		} catch (NullPointerException e) {
			mv.setViewName("loginFail");
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