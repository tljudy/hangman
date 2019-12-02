package hangman.data;

import java.util.List;

import hangmanjpa.entities.Word;

public interface WordDAO {
	Word getWordById(int id);
	List<Word> getAllWords();
	Word findWordByName(String word);
	Word addWord(Word word);
	boolean updateWord(Word word);
	long getWordCount();
	long getWordCountByDifficulty(String difficulty);
	List<Word> getAllByDifficulty(String difficulty);
}
