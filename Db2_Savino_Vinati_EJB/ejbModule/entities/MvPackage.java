package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="mv_package")
@NamedQuery(name="MvPackage.findAll", query="SELECT m FROM MvPackage m")
public class MvPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float avgoptionalproducts;

	@OneToOne
	@JoinColumn(name = "idpackage")
	private Servicepackage servicepackage;

	private int sales;

	private float value;

	private float valuewithproducts;

	public MvPackage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAvgoptionalproducts() {
		return this.avgoptionalproducts;
	}

	public void setAvgoptionalproducts(float avgoptionalproducts) {
		this.avgoptionalproducts = avgoptionalproducts;
	}

	public int getSales() {
		return this.sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getValuewithproducts() {
		return this.valuewithproducts;
	}

	public void setValuewithproducts(float valuewithproducts) {
		this.valuewithproducts = valuewithproducts;
	}

	public Servicepackage getServicepackage() {
		return servicepackage;
	}

	public void setServicepackage(Servicepackage servicepackage) {
		this.servicepackage = servicepackage;
	}

}