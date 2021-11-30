package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the servicemobile database table.
 * 
 */
@Entity
@Table(name = "servicemobile", schema="db2_savino_vinati")
@NamedQuery(name="Servicemobile.findAll", query="SELECT sm FROM Servicemobile sm")
public class Servicemobile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private float feeextraminutes;

	private float feeextrasms;

	private int includedminutes;

	private int includedsms;
	
	@OneToOne
	private Service service;

	public Servicemobile() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getFeeextraminutes() {
		return this.feeextraminutes;
	}

	public void setFeeextraminutes(float feeextraminutes) {
		this.feeextraminutes = feeextraminutes;
	}

	public float getFeeextrasms() {
		return this.feeextrasms;
	}

	public void setFeeextrasms(float feeextrasms) {
		this.feeextrasms = feeextrasms;
	}

	public int getIncludedminutes() {
		return this.includedminutes;
	}

	public void setIncludedminutes(int includedminutes) {
		this.includedminutes = includedminutes;
	}

	public int getIncludedsms() {
		return this.includedsms;
	}

	public void setIncludedsms(int includedsms) {
		this.includedsms = includedsms;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}