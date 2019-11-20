package hangmanjpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Definition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String definition;
	// JPA really hates the naming convention from the database
	@Column(name = "partofspeech")
	private String partOfSpeech;

	@ManyToOne
	@JoinColumn(name = "word_id")
	private Word word;

	@OneToMany(mappedBy = "definition", fetch = FetchType.EAGER)
	private List<Example> examples;

	public Definition(int id, String definition, String partOfSpeech, Word word) {
		this.id = id;
		this.definition = definition;
		this.partOfSpeech = partOfSpeech;
		this.word = word;
	}

	public Definition() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public ArrayList<Example> getExamples() {
		if (examples == null)
			return new ArrayList<Example>();
		return new ArrayList<Example>(examples);
	}

	public void setExamples(List<Example> examples) {
		this.examples = examples;
	}

	public void addExample(Example ex) {
		if (ex == null)
			return;
		if (examples == null) {
			examples = new ArrayList<Example>();
		}

		examples.add(ex);

		if (ex.getDefinition() != this) {
			ex.setDefinition(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Definition other = (Definition) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Definition [id=" + id + ", definition=" + definition + ", partOfSpeech=" + partOfSpeech + ", examples="
				+ examples + "]";
	}

}
