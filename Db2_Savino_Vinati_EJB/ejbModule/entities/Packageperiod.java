package entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the optionalproduct database table.
 * 
 */
@Entity
@Table(name = "packageperiod", schema="db2_savino_vinati")
@NamedQuery(name="Packageperiod.findAll", query="SELECT pp FROM Packageperiod pp")
public class Packageperiod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "idservicepackage")
	private Servicepackage servicepackage;
	
	@ManyToOne
    @JoinColumn(name = "idvalidityperiod")
	private Validityperiod validityperiod;
	
	private float monthlycost;
	
	public Packageperiod() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getMonthlycost() {
		return monthlycost;
	}

	public void setMonthlycost(float monthlycost) {
		this.monthlycost = monthlycost;
	}

	public Validityperiod getValidityperiod() {
		return validityperiod;
	}

	public void setValidityperiod(Validityperiod validityperiod) {
		this.validityperiod = validityperiod;
	}

	public Servicepackage getServicepackage() {
		return servicepackage;
	}

	public void setServicepackage(Servicepackage servicepackage) {
		this.servicepackage = servicepackage;
	}


}