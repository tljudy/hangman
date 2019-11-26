package hangman.data;

import java.util.List;

import hangmanjpa.entities.Example;

public interface ExampleDAO {
	Example getExampleById(int id);
	Example addExample(Example ex);
	List<Example> getDefinitionExamples(int definition_id);
	void deleteExample(Example ex);
}
