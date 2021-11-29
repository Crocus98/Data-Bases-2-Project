package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the validityperiod database table.
 * 
 */
@Entity
@Table(name = "validityperiod", schema="db2_savino_vinati")
@NamedQuery(name="Validityperiod.findAll", query="SELECT v FROM Validityperiod v")
public class Validityperiod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int validityperiod;
	
	@OneToMany(mappedBy = "servicepackage")
    private List<Packageperiod> packageperiod;

	public Validityperiod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValidityperiod() {
		return this.validityperiod;
	}

	public void setValidityperiod(int validityperiod) {
		this.validityperiod = validityperiod;
	}

}