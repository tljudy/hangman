package hangmanjpa.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// Database column names are gameWon/pointsAwarded/gameDate, but JPA does not like caps I guess
	@Column(name="gamewon")
	private boolean gameWon;
	@Column(name="pointsawarded")
	private int pointsAwarded;
	@Column(name="gamedate")
	private LocalDateTime gameDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "word_id")
	private Word word;

	public Game(int id, boolean gameWon, int pointsAwarded, LocalDateTime gameDate, User user, Word word) {
		this.id = id;
		this.gameWon = gameWon;
		this.pointsAwarded = pointsAwarded;
		this.gameDate = gameDate;
		this.user = user;
		this.word = word;
	}

	public Game() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isGameWon() {
		return gameWon;
	}

	public void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}

	public int getPointsAwarded() {
		return pointsAwarded;
	}

	public void setPointsAwarded(int pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}

	public LocalDateTime getGameDate() {
		return gameDate;
	}

	public void setGameDate(LocalDateTime gameDate) {
		this.gameDate = gameDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
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
		Game other = (Game) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", gameWon=" + gameWon + ", pointsAwarded=" + pointsAwarded + ", gameDate=" + gameDate
				+ ", user=" + user + ", word=" + word + "]";
	}

}
