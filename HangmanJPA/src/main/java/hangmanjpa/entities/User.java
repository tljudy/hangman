package hangmanjpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;
	private String password;
	
	// Database column names are totalPoints/preferredModelColor/preferredDifficulty, but JPA does not like caps I guess
	@Column(name="totalpoints")
	private int totalPoints;
	
	@Column(name="preferredmodelcolor")
	private String preferredModelColor;
	
	@Column(name="preferreddifficulty")
	private String preferredDifficulty;

	@OneToMany(mappedBy = "user")
	private List<Game> games;

	@OneToMany(mappedBy = "user")
	private List<UserSecretQuestion> userSecretQuestions;

	public User(int id, String username, String password, int totalPoints, String preferredModelColor,
			String preferredDifficulty) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.totalPoints = totalPoints;
		this.preferredModelColor = preferredModelColor;
		this.preferredDifficulty = preferredDifficulty;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getPreferredModelColor() {
		return preferredModelColor;
	}

	public void setPreferredModelColor(String preferredModelColor) {
		if (preferredModelColor == null) 
			return;
		this.preferredModelColor = preferredModelColor;
	}

	public String getPreferredDifficulty() {
		return preferredDifficulty;
	}

	public void setPreferredDifficulty(String preferredDifficulty) {
		if (preferredDifficulty == null) 
			return;
		this.preferredDifficulty = preferredDifficulty;
	}

	public ArrayList<Game> getGames() {
		return new ArrayList<Game>(games);
	}

	public void addGame(Game game) {
		if (game == null)
			return;
		if (games == null) {
			games = new ArrayList<Game>();
		}
		
		games.add(game);
	}

	public List<UserSecretQuestion> getUserSecretQuestions() {
		return userSecretQuestions;
	}

	public void addUserSecretQuestions(UserSecretQuestion userSecretQuestion) {
		if (userSecretQuestion == null)
			return;
		if (userSecretQuestions == null) {
			userSecretQuestions = new ArrayList<UserSecretQuestion>();
		}

		userSecretQuestions.add(userSecretQuestion);
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", totalPoints=" + totalPoints
				+ ", preferredModelColor=" + preferredModelColor + ", preferredDifficulty=" + preferredDifficulty + "]";
	}
}
