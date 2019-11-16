package hangman.data;

import java.util.List;

import hangmanjpa.entities.Game;

public interface GameDAO {
	Game getGameById(int id);
	List<Game> getAllGames();
}
