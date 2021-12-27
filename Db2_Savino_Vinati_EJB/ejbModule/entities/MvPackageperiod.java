package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mv_packageperiod database table.
 * 
 */
@Entity
@Table(name="mv_packageperiod")
@NamedQuery(name="MvPackageperiod.findAll", query="SELECT m FROM MvPackageperiod m")
public class MvPackageperiod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "idpackage")
	private Servicepackage servicepackage;

	@ManyToOne
	@JoinColumn(name = "idperiod")
	private Validityperiod validityperiod;

	private int sales;

	public MvPackageperiod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSales() {
		return this.sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public Servicepackage getServicepackage() {
		return servicepackage;
	}

	public void setServicepackage(Servicepackage servicepackage) {
		this.servicepackage = servicepackage;
	}

	public Validityperiod getValidityperiod() {
		return validityperiod;
	}

	public void setValidityperiod(Validityperiod validityperiod) {
		this.validityperiod = validityperiod;
	}

}