package hangman.data;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hangmanjpa.entities.Definition;
import hangmanjpa.entities.Example;
import hangmanjpa.entities.Word;

// Borrow some data from Dictionary.com since it has a fairly predictable layout

@Component
public class Scraper {
	@Autowired
	private WordDAO wordDAO;
	@Autowired
	private DefinitionDAO defDAO;
	@Autowired
	private ExampleDAO exDAO;

	private int syllables;
	private String partOfSpeech;
	private String word;

	public Word Scrape(String word) {
		this.word = word;
		Word wordObj = wordDAO.findWordByName(word);
		Document doc = null;

		// Word is already in the DB
		if (wordObj != null) {
			return wordObj;
		}


		// jsoup is an external library for web scraping
		try {
			doc = Jsoup.connect("https://www.dictionary.com/browse/" + word.toLowerCase()).get();
		} catch (org.jsoup.HttpStatusException ex) {
			// no such word in online database
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// Count syllables
		Elements syllablesParent = doc.getElementsByClass("evh0tcl2");
		String syls = syllablesParent.first().text().split(";")[0];
		String s = syls.split(" or ")[0];
		syllables = s.split(",")[0].split("-").length;

		// Stage a new word for the database
		Word newWord = new Word();
		newWord.setWord(word.toLowerCase());
		newWord.setSyllables(syllables);
		newWord.setDifficulty(newWord.determineDifficulty());

		// Persist the word into the database
		newWord = wordDAO.addWord(newWord);

		// Definitions are lumped inside a div: "e1hk9ate4"
		Element row = doc.getElementsByClass("e1hk9ate4").first();

		// Individual definitions
		Elements defs = row.getElementsByClass("e1q3nk1v4");

		// extra example sentences if needed
		Elements els = doc.getElementsByClass("e15kc6du6");

		for (Element def : defs) {
			// ignore bullet point headers and links (just get the subsequent definitions)
			if (def.children().size() > 0 && def.child(0).className().contains("luna-labset")) {
				continue;
			}

			Definition d = addWordDefinitions(newWord, def);

			// Add sentence/examples found elsewhere on the page
			if (d.getExamples() == null || d.getExamples().size() == 0) {
				if (els.size() > 0) {
					Example ex = new Example();

					ex.setSentence(els.get(0).text());
					addExampleToDef(d, ex);

					els.remove(0);
				}
			}
		}

		return newWord;
	}

	private Definition addWordDefinitions(Word newWord, Element def) {
		// Temporary list for definition's embedded example sentences, if any
		ArrayList<Example> examples = null;

		// Definition has one or more embedded example sentences
		if (def.children().size() > 0) {
			examples = new ArrayList<>();
			Elements els = def.children();

			for (Element child : els) {
				if (child.className().contains("luna-xref"))
					continue;

				Example ex = new Example();
				ex.setSentence(child.text());
				examples.add(ex);
				// Don't pick the same example for the next definition
				child.remove();
			}
		}

		// Staging for database
		Definition d = new Definition();
		d.setDefinition(def.text());
		d.setPartOfSpeech(partOfSpeech);
		d.setWord(newWord);

		if (d.getDefinition().toLowerCase().contains(word)) {
			d.setDefinition(d.getDefinition().replaceAll("(?i)" + word, getUnderscores()));
		}

		// Persist into database
		d = defDAO.addDefinition(d);

		// Add example(s), if any
		if (examples != null) {
			for (Example ex : examples) {
				addExampleToDef(d, ex);
			}
		}

		// Probably redundant
		newWord.addDefinition(d);

		return d;
	}

	private Example addExampleToDef(Definition def, Example ex) {
		if (ex.getSentence().toLowerCase().contains(word)) {
			ex.setSentence(ex.getSentence().replaceAll("(?i)" + word, getUnderscores()));
		}

		ex.setDefinition(def);
		def.addExample(exDAO.addExample(ex));

		return ex;
	}

	// For replacing words inside definitions/examples that give away the answer
	private String getUnderscores() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < word.length(); i++) {
			sb.append("_");
		}

		return sb.toString();
	}
	
}
