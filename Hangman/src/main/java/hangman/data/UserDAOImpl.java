package hangman.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hangmanjpa.entities.User;

@Transactional
@Service
public class UserDAOImpl implements UserDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public User getUserById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public User findUserByUsername(String username) {
		if (username == null) return null;
		
		User user = null;
		String query = "SELECT * FROM User u WHERE u.username = :email";
		
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

}
