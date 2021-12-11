package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mv_bestproduct database table.
 * 
 */
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

}