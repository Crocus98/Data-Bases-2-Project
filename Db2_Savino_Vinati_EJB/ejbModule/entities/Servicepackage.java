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
@NamedQuery(name="Servicepackage.findAll", query="SELECT sp FROM Servicepackage sp")
public class Servicepackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="packageproduct",
			joinColumns= { @JoinColumn(name="idservicepackage")},
			inverseJoinColumns= { @JoinColumn(name="idoptionalproduct")}
	)
	private List<Optionalproduct> optionalproducts;

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

	public List<Optionalproduct> getOptionalproducts() {
		return optionalproducts;
	}

	public void setOptionalproducts(List<Optionalproduct> optionalproducts) {
		this.optionalproducts = optionalproducts;
	}

}