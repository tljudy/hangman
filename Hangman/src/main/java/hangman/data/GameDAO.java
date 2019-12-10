package hangman.data;

import java.util.List;
import java.util.Map;

import hangmanjpa.entities.Game;

public interface GameDAO {
	Game getGameById(int id);
	Game addGame(Game g);
	void deleteGame(Game g);
	List<Game> getGamesByUserId(int id);
	Map<String, Integer> getLeadersLastDay();
}
