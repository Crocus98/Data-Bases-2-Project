package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the optionalproduct database table.
 * 
 */
@Entity
@Table(name = "optionalproduct", schema="db2_savino_vinati")
@NamedQuery(name="Optionalproduct.findAll", query="SELECT o FROM Optionalproduct o")
public class Optionalproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float monthlyprice;

	private String name;
	
	@ManyToMany(mappedBy="optionalproducts", fetch = FetchType.EAGER)
	private List <Servicepackage> servicepackages;

	public Optionalproduct() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getMonthlyprice() {
		return this.monthlyprice;
	}

	public void setMonthlyprice(float monthlyprice) {
		this.monthlyprice = monthlyprice;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List <Servicepackage> getServicepackages() {
		return servicepackages;
	}

	public void setServicepackages(List <Servicepackage> servicepackages) {
		this.servicepackages = servicepackages;
	}

}