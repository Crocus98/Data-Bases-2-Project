package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the serviceschedule database table.
 * 
 */
@Entity
@Table(name = "serviceschedule", schema="db2_savino_vinati")
@NamedQuery(name="Serviceschedule.findAll", query="SELECT s FROM Serviceschedule s")
public class Serviceschedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int idactivation;

	private int idservice;
	
	@ManyToOne
	@JoinColumn(name="idactivation")
	private Activationschedule activationschedule;
	
	@ManyToOne
	@JoinColumn(name="idservice")
	private Service service;

	public Serviceschedule() {
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

	public int getIdservice() {
		return this.idservice;
	}

	public void setIdservice(int idservice) {
		this.idservice = idservice;
	}

}