package hangman.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hangmanjpa.entities.Word;

@Transactional
@Service
public class WordDAOImpl implements WordDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Word getWordById(int id) {
		return em.find(Word.class, id);
	}

	@Override
	public Word findWordByName(String word) {
		if (word == null) return null;
		
		Word found = null;
		String query = "SELECT w FROM Word w WHERE w.word = :word";
		
		try {
			found = em.createQuery(query, Word.class).setParameter("word", word).getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			return null;
		} catch (Exception e) {
			
		}
		
		return found;
	}

	@Override
	public List<Word> getAllWords() {
		return em.createQuery("SELECT w FROM Word", Word.class).getResultList();
	}
	
	@Override
	public Word addWord(Word word) {
		em.persist(word);
		em.flush();
		return word;
	}
	
}
