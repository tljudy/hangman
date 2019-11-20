package hangmanjpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Example {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String sentence;

	@ManyToOne
	@JoinColumn(name = "definition_id")
	private Definition definition;

	public Example(int id, String sentence, Definition definition) {
		this.id = id;
		this.sentence = sentence;
		this.definition = definition;
	}

	public Example() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		if (sentence == null)
			return;
		this.sentence = sentence;
	}

	public Definition getDefinition() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		if (definition == null) 
			return;
		this.definition = definition;
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
		Example other = (Example) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Example [id=" + id + ", sentence=" + sentence + "]";
	}

}
