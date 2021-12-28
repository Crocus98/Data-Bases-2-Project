package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="mv_bestproduct")
@NamedQuery(name="MvBestproduct.findAll", query="SELECT m FROM MvBestproduct m")
public class MvBestproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "idoptionalproduct")
	private Optionalproduct optionalproduct;

	private float value;
	
	private int sales;

	public MvBestproduct() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Optionalproduct getOptionalproduct() {
		return optionalproduct;
	}

	public void setOptionalproduct(Optionalproduct optionalproduct) {
		this.optionalproduct = optionalproduct;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

}