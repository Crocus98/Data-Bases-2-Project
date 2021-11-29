package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the productschedule database table.
 * 
 */
@Entity
@Table(name = "productschedule", schema="db2_savino_vinati")
@NamedQuery(name="Productschedule.findAll", query="SELECT p FROM Productschedule p")
public class Productschedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int idactivation;

	private int idoptionalproduct;
	
	@ManyToOne
	@JoinColumn(name="idactivation")
	private Activationschedule activationschedule;
	
	@ManyToOne
	@JoinColumn(name="idoptionalproduct")
	private Optionalproduct optionalproduct;

	public Productschedule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdactivation() {
		return this.idactivation;
	}

	public void setIdactivation(int idactivation) {
		this.idactivation = idactivation;
	}

	public int getIdoptionalproduct() {
		return this.idoptionalproduct;
	}

	public void setIdoptionalproduct(int idoptionalproduct) {
		this.idoptionalproduct = idoptionalproduct;
	}

}