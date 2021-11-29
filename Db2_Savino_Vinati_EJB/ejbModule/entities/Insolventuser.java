package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the insolventuser database table.
 * 
 */
@Entity
@Table(name = "insolventuser", schema="db2_savino_vinati")
@NamedQuery(name="Insolventuser.findAll", query="SELECT s FROM Insolventuser s")
public class Insolventuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int failedpaymentcount;

	private boolean insolvent;
	
	@OneToOne
	private User user;

	public Insolventuser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFailedpaymentcount() {
		return failedpaymentcount;
	}

	public void setFailedpaymentcount(int failedpaymentcount) {
		this.failedpaymentcount = failedpaymentcount;
	}

	public boolean isInsolvent() {
		return insolvent;
	}

	public void setInsolvent(boolean insolvent) {
		this.insolvent = insolvent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}