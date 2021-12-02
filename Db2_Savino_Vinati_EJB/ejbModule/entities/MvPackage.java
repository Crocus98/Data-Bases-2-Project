package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mv_package database table.
 * 
 */
@Entity
@Table(name="mv_package")
@NamedQuery(name="MvPackage.findAll", query="SELECT mp FROM MvPackage mp")
public class MvPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float avgoptionalproducts;

	private String packagename;

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

	public String getPackagename() {
		return this.packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
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

}