package hangmanjpa.entities;

import java.util.ArrayList;
import java.util.List;

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
	private int totalPoints;
	private String preferredModelColor;
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
		this.preferredModelColor = preferredModelColor;
	}

	public String getPreferredDifficulty() {
		return preferredDifficulty;
	}

	public void setPreferredDifficulty(String preferredDifficulty) {
		this.preferredDifficulty = preferredDifficulty;
	}

	public ArrayList<Game> getGames() {
		return new ArrayList<Game>(games);
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public List<UserSecretQuestion> getUserSecretQuestions() {
		return userSecretQuestions;
	}

	public void setUserSecretQuestions(List<UserSecretQuestion> userSecretQuestions) {
		this.userSecretQuestions = userSecretQuestions;
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
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", totalPoints=" + totalPoints
				+ ", preferredModelColor=" + preferredModelColor + ", preferredDifficulty=" + preferredDifficulty + "]";
	}
}
