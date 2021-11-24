package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the test database table.
 * 
 */
@Entity
@Table(name = "test", schema="db2_savino_vinati")
@NamedQuery(name="Test.findAll", query="SELECT t FROM EntitiesTest t")
public class EntitiesTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String testValue;

	public EntitiesTest() {
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTestValue() {
		return this.testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

}