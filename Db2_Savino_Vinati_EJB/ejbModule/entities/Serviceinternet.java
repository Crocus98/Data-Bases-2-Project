package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the serviceinternet database table.
 * 
 */
@Entity
@Table(name = "serviceinternet", schema="db2_savino_vinati")
@NamedQuery(name="Serviceinternet.findAll", query="SELECT s FROM Serviceinternet s")
public class Serviceinternet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private float feeextragbs;

	private int includedgbs;

	public Serviceinternet() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getFeeextragbs() {
		return this.feeextragbs;
	}

	public void setFeeextragbs(float feeextragbs) {
		this.feeextragbs = feeextragbs;
	}

	public int getIncludedgbs() {
		return this.includedgbs;
	}

	public void setIncludedgbs(int includedgbs) {
		this.includedgbs = includedgbs;
	}

}