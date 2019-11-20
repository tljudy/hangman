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
		return em.createQuery("SELECT w FROM Word w", Word.class).getResultList();
	}
	
	@Override
	public long getWordCount() {
		return (long)em.createQuery("SELECT COUNT(w.id) FROM Word w").getSingleResult();
	}
	
	@Override
	public boolean updateWord(Word word) {
		Word managed = em.find(Word.class, word.getId());
		
		if (managed == null) return false;
		
		try {
			managed.setDifficulty(word.getDifficulty());
			managed.setSyllables(word.getSyllables());
			managed.setWord(word.getWord());
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public Word addWord(Word word) {
		em.persist(word);
		em.flush();
		return word;
	}
	
}
