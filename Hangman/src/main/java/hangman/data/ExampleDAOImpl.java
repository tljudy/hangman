package hangman.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hangmanjpa.entities.Example;

@Transactional
@Service
public class ExampleDAOImpl implements ExampleDAO {
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public Example getExampleById(int id) {
		return em.find(Example.class, id);
	}

	@Override
	public List<Example> getDefinitionExamples(int definition_id) {
		if (definition_id < 1) return null;
		String query = "SELECT e FROM Example e WHERE e.definition.id = :definition_id";
		
		List<Example> ex = null;
		
		try {
			ex = em.createQuery(query, Example.class).setParameter("definition_id", definition_id).getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null;
		} catch (Exception e) {
			
		}
		
		return ex;
	}
	
	@Override
	public Example addExample(Example ex) {
		em.persist(ex);
		em.flush();
		return ex;
	}
	
	@Override
	public void deleteExample(Example ex) {
		ex = this.getExampleById(ex.getId());
		em.remove(ex);
		em.flush();
	}

}
