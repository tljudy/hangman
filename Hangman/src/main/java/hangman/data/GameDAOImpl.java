package hangman.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hangmanjpa.entities.Game;

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
		g.setGameDate(new Date());
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
	public Map<Integer, String> getLeadersLastDay() {
		String query = "SELECT g FROM Game g WHERE g.gameDate > :yesterday";
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DATE, -1);

		List<Game> games = null;
		Map<Integer, String> results = new HashMap<Integer, String>();

		try {
			games = em.createQuery(query, Game.class).setParameter("yesterday", dateFormat.format(cal.getTime()))
					.getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null;
		}
		
		
		for (Game g : games) {
			int user = g.getUser().getId();
			results.put(user, results.get(user) + g.getPointsAwarded());
		}

		return results;
	}
}
