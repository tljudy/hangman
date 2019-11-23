package hangman.data;

import java.util.List;

import hangmanjpa.entities.SecretQuestion;
import hangmanjpa.entities.UserSecretQuestion;

public interface SecretQuestionDAO {
	SecretQuestion getSecretQuestionById(int id);
	UserSecretQuestion getUserSecretQuestionById(int id);
	List<UserSecretQuestion> getUserAnswersByUserID(int user_id);
	List<SecretQuestion> getAllSecretQuestions();
	UserSecretQuestion addUserAnswer(int userId, int questionId, String answer);
}
