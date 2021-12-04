package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mv_alerts database table.
 * 
 */
@Entity
@Table(name="mv_alerts")
@NamedQuery(name="MvAlert.findAll", query="SELECT m FROM MvAlert m")
public class MvAlert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private Alert alert;

	public MvAlert() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

}