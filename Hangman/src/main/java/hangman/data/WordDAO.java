package hangman.data;

import java.util.List;

import hangmanjpa.entities.Word;

public interface WordDAO {
	Word getWordById(int id);
	List<Word> getAllWords();
}
