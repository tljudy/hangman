package hangmanjpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Word {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String word;
	private String difficulty;
	private int syllables;

	@OneToMany(mappedBy = "word")
	private List<Definition> definitions;

	@JsonIgnore
	@OneToMany(mappedBy = "word")
	private List<Game> games;

	public Word(String word, String difficulty, int syllables) {
		this.word = word;
		this.difficulty = difficulty;
		this.syllables = syllables;
	}

	public Word() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public String determineDifficulty() {
		return syllables < 4 
				? "Easy" 
						: syllables <= 6
						? "Medium" 
								: "Hard";
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public int getSyllables() {
		return syllables;
	}

	public void setSyllables(int syllables) {
		this.syllables = syllables;
	}

	public ArrayList<Definition> getDefinitions() {
		if (definitions != null) {
			return new ArrayList<Definition>(definitions);
		}
		return null;
	}
	
	public void setDefinitions(List<Definition> definitions) {
		this.definitions = definitions;
	}


	public void addDefinition(Definition definition) {
		if (definition == null) return;
		if (definitions == null) definitions = new ArrayList<Definition>();
		this.definitions.add(definition);
		
		if (definition.getWord() != this) {
			definition.setWord(this);
		}
	}

	public List<Game> getGames() {
		return games;
	}

	public void addGame(Game game) {
		if (game == null) 
			return;
		if (games == null) {
			games = new ArrayList<Game>();
		}
		
		this.games.add(game);
		
		if (game.getWord() != this) {
			game.setWord(this);
		}
		
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Word [id=" + id + ", word=" + word + ", difficulty=" + difficulty + ", syllables=" + syllables
				+ ", definitions=" + definitions + "]";
	}

}
