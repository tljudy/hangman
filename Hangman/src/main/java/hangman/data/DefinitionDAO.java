package hangman.data;

import java.util.List;

import hangmanjpa.entities.Definition;
import hangmanjpa.entities.Word;

public interface DefinitionDAO {
	Definition getDefinitionById(int id);
	List<Definition> getWordDefinitions(Word word);
	Definition addDefinition(Definition def);
}
