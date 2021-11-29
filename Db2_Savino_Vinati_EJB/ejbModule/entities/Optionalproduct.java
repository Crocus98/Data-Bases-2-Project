package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the optionalproduct database table.
 * 
 */
@Entity
@NamedQuery(name="Optionalproduct.findAll", query="SELECT o FROM Optionalproduct o")
public class Optionalproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 
	private int idservicetype;

	private float monthlyprice;

	private String name;
	
	
	@ManyToOne
	@JoinColumn(name="idservicetype")
	private Servicetype serviceType;

	public Optionalproduct() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdservicetype() {
		return this.idservicetype;
	}

	public void setIdservicetype(int idservicetype) {
		this.idservicetype = idservicetype;
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