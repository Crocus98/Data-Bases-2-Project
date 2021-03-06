package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "serviceinternet", schema="db2_savino_vinati")
@NamedQuery(name="Serviceinternet.findAll", query="SELECT si FROM Serviceinternet si")
public class Serviceinternet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	private float feeextragbs;

	private int includedgbs;
	
	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	private Service service;

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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}