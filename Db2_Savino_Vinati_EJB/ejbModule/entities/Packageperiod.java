package entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the optionalproduct database table.
 * 
 */
@Entity
@Table(name = "packageperiod", schema="db2_savino_vinati")
@NamedQuery(name="Packageperiod.findAll", query="SELECT o FROM Optionalproduct o")
public class Packageperiod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@ManyToOne
    @JoinColumn(name = "idservicepackage")
    Servicepackage servicepackage;
	
	@ManyToOne
    @JoinColumn(name = "idvalidityperiod")
    Validityperiod validityperiod;
	
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


}