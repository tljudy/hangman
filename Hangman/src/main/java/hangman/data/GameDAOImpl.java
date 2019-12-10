package hangman.data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hangmanjpa.entities.Game;
import hangmanjpa.entities.User;

@Transactional
@Service
public class GameDAOImpl implements GameDAO {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	UserDAO userDAO;

	@Override
	public Game getGameById(int id) {
		return em.find(Game.class, id);
	}

	@Override
	public List<Game> getGamesByUserId(int id) {
		String query = "SELECT g FROM Game g WHERE g.user.id = :user_id";

		List<Game> games = null;

		try {
			games = em.createQuery(query, Game.class).setParameter("user_id", id).getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null;
		}

		return games;
	}

	@Override
	public Game addGame(Game g) {
		g.setGameDate(LocalDateTime.now());
		em.persist(g);
		em.flush();
		return g;
	}

	@Override
	public void deleteGame(Game g) {
		g = this.getGameById(g.getId());
		em.remove(g);
		em.flush();
	}

	@Override
	public Map<String, String> getLeadersLastDay() {
		String query = "SELECT g FROM Game g WHERE g.gameDate > :yesterday";
		LocalDateTime now = LocalDateTime.now().minusDays(1);
		List<Game> games = null;
		Map<String, String> results = new HashMap<String, String>();

		try {
			games = em.createQuery(query, Game.class).setParameter("yesterday", now)
					.getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null;
		}
		
		
		for (Game g : games) {
			User user = userDAO.getUserById(g.getUser().getId());
			results.put(user.getUsername(), results.get(user.getUsername()) + g.getPointsAwarded());
		}

		return results;
	}
}
