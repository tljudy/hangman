package hangman.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hangman.data.DefinitionDAO;
import hangman.data.ExampleDAO;
import hangman.data.GameDAO;
import hangman.data.SecretQuestionDAO;
import hangman.data.UserDAO;
import hangman.data.WordDAO;
import hangmanjpa.entities.Definition;
import hangmanjpa.entities.Example;
import hangmanjpa.entities.Game;
import hangmanjpa.entities.User;
import hangmanjpa.entities.Word;

@RestController
public class MainController {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private GameDAO gameDAO;
	@Autowired
	private WordDAO wordDAO;
	@Autowired
	private DefinitionDAO defDAO;
	@Autowired
	private ExampleDAO exDAO;
	@Autowired
	private SecretQuestionDAO qDAO;

	@RequestMapping(path = { "/", "home.do" }, method = RequestMethod.GET)
	public ModelAndView index(HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("questions", qDAO.getAllSecretQuestions());
		mv.addObject("leaders", userDAO.getLeadersByPoints());
		User user = (User)session.getAttribute("user");
		
		if (user != null) {
			mv.addObject("history", gameDAO.getGamesByUserId(user.getId()));
		}
		return mv;
	}

	// Random word by difficulty
	@RequestMapping(path = { "newGame.do" }, method = RequestMethod.GET)
	public ModelAndView newGame(String difficulty, HttpSession session) {
		if (difficulty == null)
			difficulty = "easy";

		// TODO: if playing == true (logged in user still has an unfinished game
		// running), mark game as a loss and deduct points
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

		int hintsAvailable = 0;

		for (Definition d : w.getDefinitions()) {
			hintsAvailable++;
			for (Example e : d.getExamples()) {
				hintsAvailable++;
			}
		}

		session.setAttribute("word", w);
		session.setAttribute("difficulty", difficulty);
		session.setAttribute("guesses", new ArrayList<String>());
		session.setAttribute("playing", true);
		session.setAttribute("messages", new ArrayList<String>());
		session.setAttribute("hintsPurchased", 0);
		session.setAttribute("hintsAvailable", hintsAvailable);

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

		return populateModel(session);
	}

	// Check guess
	@SuppressWarnings("unchecked")
	@RequestMapping(path = { "guess.do" })
	public ModelAndView makeGuess(@RequestBody String ltr, HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("leaders", userDAO.getLeadersByPoints());

		String letter = null;
		
		if (ltr == null || ltr.split("=").length < 2) {
			return mv;
		}
		letter = ltr.split("=")[1];

		Boolean playing = (Boolean) session.getAttribute("playing");

		// no guess provided or no game is active
		if (letter == null || playing == null || !playing)
			return mv;

		ArrayList<String> guesses = (ArrayList<String>) session.getAttribute("guesses");
		ArrayList<String> messages = (ArrayList<String>) session.getAttribute("messages");

		// check valid guesses
		if (!letter.matches("[a-zA-Z]{1}")) {
			messages.add("The character \"" + letter + "\" is invalid");
			session.setAttribute("messages", messages);
			mv.addObject("messages", session.getAttribute("messages"));
			mv.addObject("wordString", ((Word) session.getAttribute("word")).getWord());
			return mv;
		}

		Word w = (Word) session.getAttribute("word");

		// letter not guessed yet
		if (!guesses.contains(letter.toUpperCase()) && guesses.add(letter.toUpperCase())) {
			Collections.sort(guesses);
			session.setAttribute("guesses", guesses);
			// correct guess
			if (w.getWord().contains(letter.toLowerCase())) {
				mv.addObject("wordString", maskWord(((Word) session.getAttribute("word")).getWord(),
						(ArrayList<String>) session.getAttribute("guesses")));
				// winner
				if (checkWin(session)) {
					return endGame(session);
				}
			} else {
				// incorrect guess
				session.setAttribute("guessesRemaining", (int) session.getAttribute("guessesRemaining") - 1);
				if (!checkGuessesRemaining(session)) {
					// loser
					return endGame(session);
				}
			}
		} else {
			// already guessed
			messages.add("The letter \"" + letter + "\" has already been guessed");
			session.setAttribute("messages", messages);
		}

		return populateModel(session);
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
				if (letter.equals("-")) {
					masked.append("-");
				}else {
					masked.append("_");
				}
			} else {
				masked.append(letter);
			}
		}

		return masked.toString();
	}

	private boolean checkGuessesRemaining(HttpSession session) {
		return ((int) session.getAttribute("guessesRemaining") > 0);
	}

	private boolean checkWin(HttpSession session) {
		String word = ((Word) session.getAttribute("word")).getWord();
		ArrayList<String> guesses = (ArrayList<String>) session.getAttribute("guesses");

		for (int i = 0; i < word.length(); i++) {
			if (!guesses.contains(word.substring(i, i + 1).toUpperCase())) {
				return false;
			}
		}

		return true;
	}

	private ModelAndView endGame(HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		String word = ((Word) session.getAttribute("word")).getWord();
		int guessesRemaining = (int) session.getAttribute("guessesRemaining");
		ArrayList<String> messages = (ArrayList<String>) session.getAttribute("messages");
		User user = (User) session.getAttribute("user");

		if (guessesRemaining <= 0) {
			messages.add("You lose! The word was '" + word.toUpperCase() + "'!");

		} else {
			messages.add("WINNER! The word was '" + word.toUpperCase() + "'!");
		}

		if (session.getAttribute("user") != null) {
			int points = calculatePoints(session);
			user.setTotalPoints(user.getTotalPoints() + points);
			user = userDAO.update(user);
			session.setAttribute("user", user);
			mv.addObject("user", session.getAttribute("user"));
			
			Game game = new Game();
			game.setPointsAwarded(points);
			game.setUser(user);
			game.setWord((Word) session.getAttribute("word"));

			if (guessesRemaining <= 0) {
				messages.add("You lost " + points + " and now have " + user.getTotalPoints() + " total points.");
				game.setGameWon(false);
			} else {
				messages.add("You won " + points + " and now have " + user.getTotalPoints() + " total points.");
				game.setGameWon(true);
			}
			gameDAO.addGame(game);
		}
		

		session.setAttribute("playing", false);
		mv.addObject("wordString", word.toUpperCase());
		mv.addObject("difficulty", session.getAttribute("difficulty"));
		mv.addObject("leaders", userDAO.getLeadersByPoints());

		return mv;
	}

	@RequestMapping(path = "buyHint.do", method = RequestMethod.GET)
	public ModelAndView buyHint(HttpSession session) {
		if ((User) session.getAttribute("user") == null || (Word) session.getAttribute("word") == null
				|| !(boolean) session.getAttribute("playing")) {
			return new ModelAndView("index");
		}

		ArrayList<String> messages = (ArrayList<String>) session.getAttribute("messages");
		ModelAndView mv = populateModel(session);
		mv.addObject("leaders", userDAO.getLeadersByPoints());
		User user = (User) session.getAttribute("user");

		int counter = 0;
		int startAt = (int) session.getAttribute("hintsPurchased");

		if (user.getTotalPoints() > 50 && startAt <= (int) session.getAttribute("hintsAvailable")) {
			for (Definition d : ((ArrayList<Definition>) ((Word) session.getAttribute("word")).getDefinitions())) {
				if (counter == startAt) {
					messages.add("" + "<div class='hint'>" + "<b>Hint Type</b>: Definition. <b>Part of Speech</b>: "
							+ d.getPartOfSpeech() + "<div class='hint'><b>Definition</b>: " + d.getDefinition()
							+ "</div>" + "</div>");
					session.setAttribute("hintsPurchased", (int) session.getAttribute("hintsPurchased") + 1);
					mv.addObject("messages", session.getAttribute("messages"));
					user.setTotalPoints(user.getTotalPoints() - 50);
					userDAO.update(user);
					return mv;
				} else {
					counter++;
					for (Example ex : d.getExamples()) {
						if (counter == startAt) {
							messages.add("" + "<div class='hint'>" + "<b>Hint Type</b>: Example Sentence. "
									+ "<div class='hint'><b>Sentence</b>: " + ex.getSentence() + "</div>" + "</div>");
							session.setAttribute("hintsPurchased", (int) session.getAttribute("hintsPurchased") + 1);
							mv.addObject("messages", session.getAttribute("messages"));
							user.setTotalPoints(user.getTotalPoints() - 50);
							userDAO.update(user);
							return mv;
						} else {
							counter++;
						}
					}
				}
			}
		} else {
			if (user.getTotalPoints() < 50) {
				messages.add("You must have at least 50 points in order to purchase a hint");
			} else
				messages.add("No more hints available for purchase");
		}
		mv.addObject("messages", session.getAttribute("messages"));
		return mv;

	}

	private ModelAndView populateModel(HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("wordString", maskWord(((Word) session.getAttribute("word")).getWord(),
				(ArrayList<String>) session.getAttribute("guesses")));
		mv.addObject("questions", qDAO.getAllSecretQuestions());
		mv.addObject("difficulty", session.getAttribute("difficulty"));
		mv.addObject("guesses", (ArrayList<String>) session.getAttribute("guesses"));
		mv.addObject("guessesRemaining", session.getAttribute("guessesRemaining"));
		mv.addObject("hintsAvailable", session.getAttribute("hintsAvailable"));
		mv.addObject("hintsPurchased", session.getAttribute("hintsPurchased"));
		mv.addObject("leaders", userDAO.getLeadersByPoints());

		if (session.getAttribute("user") != null) {
			User user = (User)session.getAttribute("user");
			mv.addObject("user", user);
			mv.addObject("history", gameDAO.getGamesByUserId(user.getId()));
		}

		return mv;
	}

	private int calculatePoints(HttpSession session) {
		int points = 0;
		String difficulty = (String) session.getAttribute("difficulty");

		switch (difficulty) {
		case "hard":
			points = 300;
			break;
		case "medium":
			points = 200;
			break;
		default:
			points = 100;
		}

		// subtract double for a loss
		if ((int) session.getAttribute("guessesRemaining") <= 0) {
			points *= -2;
		}

		return points;
	}

}
