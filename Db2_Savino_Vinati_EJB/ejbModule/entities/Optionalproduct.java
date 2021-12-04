package entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the optionalproduct database table.
 * 
 */
@Entity
@Table(name = "optionalproduct", schema="db2_savino_vinati")
@NamedQuery(name="Optionalproduct.findAll", query="SELECT op FROM Optionalproduct op")
public class Optionalproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float monthlyprice;

	private String name;

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

}