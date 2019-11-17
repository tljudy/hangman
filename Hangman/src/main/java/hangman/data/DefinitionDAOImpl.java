package hangman.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hangmanjpa.entities.Definition;
import hangmanjpa.entities.Word;

@Transactional
@Service
public class DefinitionDAOImpl implements DefinitionDAO {
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public Definition getDefinitionById(int id) {
		return em.find(Definition.class, id);
	}

	@Override
	public List<Definition> getWordDefinitions(Word word) {
		if (word == null) return null;
		String query = "SELECT d FROM Definition d WHERE d.word_id = :word_id";
		
		List<Definition> defs = null;
		
		try {
			defs = em.createQuery(query, Definition.class).setParameter("word_id", word).getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null;
		} catch (Exception e) {
			
		}
		
		return defs;
	}
	
	@Override
	public Definition addDefinition(Definition def) {
		em.persist(def);
		em.flush();
		return def;
	}

}