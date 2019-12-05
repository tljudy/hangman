package hangman.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.DefinitionDAO;
import hangman.data.ExampleDAO;
import hangman.data.SecretQuestionDAO;
import hangman.data.UserDAO;
import hangman.data.WordDAO;
import hangmanjpa.entities.Definition;
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
	private ExampleDAO exDAO;
	@Autowired
	private SecretQuestionDAO qDAO;

	@RequestMapping(path = { "/", "home.do" }, method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("questions", qDAO.getAllSecretQuestions());
		return mv;
	}

	// Random word by difficulty
	@RequestMapping(path = { "newGame.do" }, method = RequestMethod.GET)
	public ModelAndView newGame(String difficulty, HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		if (difficulty == null)
			return mv;


		// TODO: if playing == true (logged in user still has an unfinished game running), mark game as a loss and deduct points
//		if (session.getAttribute("playing").equals("true")) {
//
//		}
		
		

		List<Word> words = wordDAO.getAllByDifficulty(difficulty);

		Word w = null;

		w = wordDAO.getWordById(words.get((int) (Math.random() * words.size())).getId());
		w.setDefinitions(defDAO.getWordDefinitions(w));

		for (Definition d : w.getDefinitions()) {
			d.setExamples(exDAO.getDefinitionExamples(d.getId()));
		}

		session.setAttribute("word", w);
		session.setAttribute("difficulty", difficulty);
		session.setAttribute("guesses", new ArrayList<String>());
		session.setAttribute("playing", session.getAttribute("user") == null ? false : true);
		session.setAttribute("messages", new ArrayList<String>());
		
		switch (difficulty) {
			case "hard":
				session.setAttribute("guessesRemaining", 5);
				break; 
			case "medium":
				session.setAttribute("guessesRemaining", 7);
				break; 
			default:
				session.setAttribute("guessesRemaining", 10);
		}
		
		
		mv.addObject("word", session.getAttribute("user") == null ? "" : maskWord(((Word)session.getAttribute("word")).getWord(), (ArrayList<String>)session.getAttribute("guesses")));
		mv.addObject("difficulty", difficulty);
		mv.addObject("questions", qDAO.getAllSecretQuestions());
		mv.addObject("guesses", session.getAttribute("guesses"));
		mv.addObject("messages", session.getAttribute("messages"));
		mv.addObject("guessesRemaining", session.getAttribute("guessesRemaining"));
		
		if (session.getAttribute("user") != null) {
			mv.addObject("user", session.getAttribute("user"));
		}

		return mv;
	}

	// Check guess
	@SuppressWarnings("unchecked")
	@RequestMapping(path = { "guess.do" })
	public ModelAndView makeGuess(@RequestBody String ltr, HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		String letter = ltr.split("=")[1];
		
		if (letter == null || session.getAttribute("playing") == null || !session.getAttribute("playing").equals("true"))
			return mv;
		
		
		ArrayList<String> guesses = (ArrayList<String>)session.getAttribute("guesses");
		ArrayList<String> messages = (ArrayList<String>)session.getAttribute("messages");
		
		// check valid guesses
		if (!letter.matches("[a-zA-Z{1}]")) {
			messages.add("The character \"" + letter + "\" is invalid");
			session.setAttribute("messages", messages);
			mv.addObject("messages", session.getAttribute("messages"));
			mv.addObject("word", ((Word)session.getAttribute("word")).getWord());
			return mv;
		}
		
		Word w = (Word) session.getAttribute("word");

		// letter not guessed yet
		if (!guesses.contains(letter.toUpperCase()) && guesses.add(letter.toUpperCase())) {
			Collections.sort(guesses);
			session.setAttribute("guesses", guesses);
			// correct guess
			if (w.getWord().contains(letter.toLowerCase())) {
				mv.addObject("word", maskWord(((Word)session.getAttribute("word")).getWord(), (ArrayList<String>)session.getAttribute("guesses")));
				// winner
				if (checkWin(session)) {
					return winGame(session);
				}
			} else {
				// incorrect guess
				if (checkGuessesRemaining(session)) {
					session.setAttribute("guessesRemaining", (int)session.getAttribute("guessesRemaining") - 1);
				}else {
					// loser
					return loseGame(session);
				}
			}
		} else {
		// already guessed
			messages.add("The letter \"" + letter + "\" has already been guessed");
			session.setAttribute("messages", messages);
			mv.addObject("messages", session.getAttribute("messages"));
		}
		
		
		mv.addObject("word", maskWord(((Word)session.getAttribute("word")).getWord(), (ArrayList<String>)session.getAttribute("guesses")));
		mv.addObject("questions", qDAO.getAllSecretQuestions());
		mv.addObject("difficulty", session.getAttribute("difficulty"));
		mv.addObject("guesses", (ArrayList<String>)session.getAttribute("guesses"));
		mv.addObject("guessesRemaining", session.getAttribute("guessesRemaining"));

		
		if (session.getAttribute("user") != null) {
			mv.addObject("user", session.getAttribute("user"));
		}
		
		mv.setStatus(HttpStatus.OK);
		return mv;
	}

	@RequestMapping(path = "about", method = RequestMethod.GET)
	public ModelAndView about() {
		return new ModelAndView("about");
	}
	
	private String maskWord(String word, ArrayList<String> guesses) {
		StringBuilder masked = new StringBuilder();
		String[] arr = word.split("");
		
		for (int i = 0; i < arr.length; i++) {
			String letter = word.substring(i, i + 1).toUpperCase();
			if (!guesses.contains(letter)) {
				masked.append("_");
			}else {
				masked.append(letter);
			}
		}
		
		return masked.toString();
	}
	
	private boolean checkGuessesRemaining(HttpSession session) {
		return ((int)session.getAttribute("guessesRemaining") > 0);
	}
	
	private boolean checkWin(HttpSession session) {
		String word = ((Word)session.getAttribute("word")).getWord();
		ArrayList<String> guesses = (ArrayList<String>)session.getAttribute("guesses");
		
		for (int i = 0; i < word.length(); i++) {
			if (!guesses.contains(word.substring(i, i + 1).toUpperCase())) {
				return false;
			}
		}
		
		return true;
	}
	
	private ModelAndView loseGame(HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		String word = ((Word)session.getAttribute("word")).getWord();
		ArrayList<String> messages = (ArrayList<String>)session.getAttribute("messages");
		messages.add("You lose! The word was '" + word + "'!");
		session.setAttribute("playing", "false");
		mv.addObject("word", word);
		mv.addObject("difficulty", session.getAttribute("difficulty"));
		
		return mv;
	}
	
	private ModelAndView winGame(HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		String word = ((Word)session.getAttribute("word")).getWord();
		ArrayList<String> messages = (ArrayList<String>)session.getAttribute("messages");
		messages.add("WINNER! The word was '" + word + "'!");
		session.setAttribute("playing", "false");
		mv.addObject("word", word);
		mv.addObject("difficulty", session.getAttribute("difficulty"));
		
		return mv;
	}

}
