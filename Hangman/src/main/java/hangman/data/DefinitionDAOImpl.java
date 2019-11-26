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
		if (word == null)
			return null;
		String query = "SELECT d FROM Definition d WHERE d.word.id = " + word.getId();

		List<Definition> defs = null;

		try {
			defs = em.createQuery(query, Definition.class).getResultList();
		} catch (javax.persistence.NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {

		}

		return defs;
	}

	@Override
	public List<Definition> getDefinitionsByString(String str) {
		List<Definition> defs = null;
		String query;

		if (str == null || str.equals("")) {
			query = "SELECT d FROM Definition d WHERE d.definition = ''";
			try {
				defs = em.createQuery(query, Definition.class).getResultList();
			} catch (javax.persistence.NoResultException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {

			}
		} else {
			query = "SELECT d FROM Definition d WHERE d.definition LIKE CONCAT('%', :string, '%')";
			try {
				defs = em.createQuery(query, Definition.class).setParameter("string", str).getResultList();
			} catch (javax.persistence.NoResultException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				
			}
		}


		return defs;
	}

	@Override
	public Definition addDefinition(Definition def) {
		em.persist(def);
		em.flush();
		return def;
	}
	
	@Override
	public void removeDefinition(Definition def) {
		def = this.getDefinitionById(def.getId());
		em.remove(def);
		em.flush();
	}

}
