package hangman.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.DefinitionDAO;
import hangman.data.Scraper;
import hangman.data.SecretQuestionDAO;
import hangman.data.UserDAO;
import hangman.data.WordDAO;
import hangmanjpa.entities.Word;

@RestController
public class MainController {
	@Autowired
	private UserDAO user;
	@Autowired
	private WordDAO wordDAO;
	@Autowired
	private DefinitionDAO defDAO;
	@Autowired
	private SecretQuestionDAO qDAO;

	@RequestMapping(path = { "/", "home.do" }, method = RequestMethod.GET)
	public ModelAndView index() throws IOException {
		ModelAndView mv = new ModelAndView("index");

		mv.addObject("questions", qDAO.getAllSecretQuestions());
		return mv;
	}

	@RequestMapping(path = { "getWord.do" }, method = RequestMethod.GET)
	public ModelAndView getWord() throws IOException {
		ModelAndView mv = new ModelAndView("index");
		long count = wordDAO.getWordCount();
		
		Word w = null;
		
		while (w == null) {
			w = wordDAO.getWordById((int)Math.round(Math.random() * count + 1));
		}
		
		w.setDefinitions(defDAO.getWordDefinitions(w));

		mv.addObject("word", w);
		mv.addObject("questions", qDAO.getAllSecretQuestions());


		return mv;
	}
	
	@RequestMapping(path = "about", method = RequestMethod.GET)
	public ModelAndView about() {
		return new ModelAndView("about");
	}
	
	

}
