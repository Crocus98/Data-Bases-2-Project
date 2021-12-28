package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="mv_suspendedorders")
@NamedQuery(name="MvSuspendedorder.findAll", query="SELECT m FROM MvSuspendedorder m")
public class MvSuspendedorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "idorder")
	private Order suspendedorder;

	public MvSuspendedorder() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getSuspendedorder() {
		return suspendedorder;
	}

	public void setSuspendedorder(Order suspendedorder) {
		this.suspendedorder = suspendedorder;
	}

}