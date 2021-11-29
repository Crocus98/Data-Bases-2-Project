package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the servicepackage database table.
 * 
 */
@Entity
@Table(name = "servicepackage", schema="db2_savino_vinati")
@NamedQuery(name="Servicepackage.findAll", query="SELECT s FROM Servicepackage s")
public class Servicepackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	
	@ManyToMany
	@JoinTable(name="packageproduct",
	joinColumns=@JoinColumn(name="idservicepackage"),
	inverseJoinColumns=@JoinColumn(name="idoptionalproduct"))
	private List<Optionalproduct> optionalproduct;

	@OneToMany(mappedBy = "servicepackage")
    private List<Packageperiod> packageperiod;
	 

	public Servicepackage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}