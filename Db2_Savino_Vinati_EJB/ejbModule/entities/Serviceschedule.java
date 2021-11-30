package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the serviceschedule database table.
 * 
 */
@Entity
@Table(name = "serviceschedule", schema="db2_savino_vinati")
@NamedQuery(name="Serviceschedule.findAll", query="SELECT ss FROM Serviceschedule ss")
public class Serviceschedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Activationschedule getActivationschedule() {
		return activationschedule;
	}

	public void setActivationschedule(Activationschedule activationschedule) {
		this.activationschedule = activationschedule;
	}

}