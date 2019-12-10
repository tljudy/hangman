package hangman.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hangmanjpa.entities.User;
import hangmanjpa.entities.UserSecretQuestion;

@Transactional
@Service
public class UserDAOImpl implements UserDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	SecretQuestionDAO ansDAO;
	
	@Override
	public User getUserById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public User findUserByUsername(String username) {
		if (username == null) return null;
		
		User user = null;
		String query = "SELECT u FROM User u WHERE u.username = :username";
		
		try {
			user = em.createQuery(query, User.class).setParameter("username", username).getSingleResult();
		}catch (Exception e) {
			
		}
		
		return user;
	}
	
	@Override
	public List<User> getAllUsers(){
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}
	
	@Override
	public User addUser(UserDTO userDTO) {
		if (userDTO == null || userDTO.getUsername() == null) return null;
		
		User user = null;
		
		try{
			user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
				.setParameter("username", userDTO.getUsername())
				.getSingleResult();
		}catch (NoResultException e) {
			
			user = new User();
			user.setUsername(userDTO.getUsername());
			user.setPassword(userDTO.getPassword());
			
			em.persist(user);
			em.flush();
			
			UserSecretQuestion ans = ansDAO.addUserAnswer(user.getId(), userDTO.getQuestions1(), userDTO.getAnswer1());
			user.addUserSecretQuestions(ans);
			
			if (userDTO.getAnswer2() != null && userDTO.getAnswer2().trim() != "" && userDTO.getQuestions2() > 0) {
				ans = ansDAO.addUserAnswer(user.getId(), userDTO.getQuestions2(), userDTO.getAnswer2());
				user.addUserSecretQuestions(ans);
			}
			if (userDTO.getAnswer3() != null && userDTO.getAnswer3().trim() != "" && userDTO.getQuestions3() > 0) {
				ans = ansDAO.addUserAnswer(user.getId(), userDTO.getQuestions3(), userDTO.getAnswer3());
				user.addUserSecretQuestions(ans);
			}
			
		}
		
		return user;
	}
	
	@Override
	public User update(User user) {
		User managed = em.find(User.class, user.getId());
		managed.setPassword(user.getPassword());
		managed.setPreferredDifficulty(user.getPreferredDifficulty());
		managed.setPreferredModelColor(user.getPreferredModelColor());
		managed.setTotalPoints(user.getTotalPoints());
		managed.setUsername(user.getUsername());
		em.persist(managed);
		em.flush();
		
		return managed;
	}
	

	@Override
	public List<User> getLeadersByPoints() {
		String query = "SELECT u FROM User u ORDER BY u.totalPoints DESC";

		List<User> users = null;

		try {
			users = em.createQuery(query, User.class).getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null;
		}

		return users;
	}
}
