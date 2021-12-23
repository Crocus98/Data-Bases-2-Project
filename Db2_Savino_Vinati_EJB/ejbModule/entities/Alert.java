package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the alert database table.
 * 
 */
@Entity
@Table(name = "alert", schema="db2_savino_vinati")
@NamedQuery(name="Alert.findAll", query="SELECT a FROM Alert a")
public class Alert implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="iduser")
	private User user;
	
	@OneToOne
	@JoinColumn(name="idorder")
	private Order order;
	
	private float amount;
	
	private boolean active;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastrejection;
	
	public Alert() {
		
	}
	
	public Alert(User user, float amount, Order order) {
		this.user = user;
		this.amount = amount;
		this.order = order;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Date getLastrejection() {
		return lastrejection;
	}
	
	public void setLastrejection(Date lastrejection) {
		this.lastrejection = lastrejection;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}