 package entities;

import java.io.Serializable;
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
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