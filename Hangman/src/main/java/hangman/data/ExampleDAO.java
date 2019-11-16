package hangman.data;

import java.util.List;

import hangmanjpa.entities.Definition;
import hangmanjpa.entities.Example;

public interface ExampleDAO {
	Example getExampleById(int id);
	List<Example> getDefinitionExamples(Definition def);
}
