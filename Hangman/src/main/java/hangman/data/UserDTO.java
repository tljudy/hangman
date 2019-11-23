package hangman.data;

public class UserDTO {
	private int id;
	private String username;
	private String password;
	private int questions1;
	private int questions2;
	private int questions3;
	private String answer1;
	private String answer2;
	private String answer3;

	public int getQuestions1() {
		return questions1;
	}

	public void setQuestions1(int questions1) {
		this.questions1 = questions1;
	}

	public int getQuestions2() {
		return questions2;
	}

	public void setQuestions2(int questions2) {
		this.questions2 = questions2;
	}

	public int getQuestions3() {
		return questions3;
	}

	public void setQuestions3(int questions3) {
		this.questions3 = questions3;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
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

}
