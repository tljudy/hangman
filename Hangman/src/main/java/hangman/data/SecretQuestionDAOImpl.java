package hangman.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hangmanjpa.entities.SecretQuestion;
import hangmanjpa.entities.UserSecretQuestion;

@Transactional
@Service
public class SecretQuestionDAOImpl implements SecretQuestionDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public SecretQuestion getSecretQuestionById(int id) {
		return em.find(SecretQuestion.class, id);
	}

	@Override
	public List<SecretQuestion> getAllSecretQuestions() {
		List<SecretQuestion> q = null;
		String query = "SELECT q FROM SecretQuestion q";

		try {
			q = em.createQuery(query, SecretQuestion.class).getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null;
		} catch (Exception e) {

		}

		return q;
	}

	@Override
	public UserSecretQuestion getUserSecretQuestionById(int id) {
		return em.find(UserSecretQuestion.class, id);
	}

	@Override
	public List<UserSecretQuestion> getUserAnswersByUserID(int user_id) {
		if (user_id < 1)
			return null;
		String query = "SELECT a FROM UserSecretQuestion a WHERE a.user.user_id = :user_id";

		List<UserSecretQuestion> ans = null;

		try {
			ans = em.createQuery(query, UserSecretQuestion.class).setParameter("user_id", user_id).getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null;
		} catch (Exception e) {

		}

		return ans;
	}

	@Override
	public UserSecretQuestion addUserAnswer(int userId, int questionId, String answer) {
		if (answer == null || questionId <= 0 || userId <= 0) return null;

		String query = "SELECT a FROM UserSecretQuestion a WHERE a.user.id = :userId AND a.question.id = :questionId";
		UserSecretQuestion managed = null; 
				
		try {
			managed = em.createQuery(query, UserSecretQuestion.class)
					.setParameter("userId", userId)
					.setParameter("questionId", questionId)
					.getSingleResult();
			managed.setAnswer(answer);
		} catch (javax.persistence.NoResultException e) {
			managed = new UserSecretQuestion();
			managed.setAnswer(answer);
			managed.setUser(userDAO.getUserById(userId));
			managed.setQuestion(getSecretQuestionById(questionId));
		
			em.persist(managed);
			em.flush();
		}
		
		return managed;
	}

}
