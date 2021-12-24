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
	@JoinColumn(name="idinsolventuser")
	private Insolventuser insolventUser;
	
	@OneToOne
	@JoinColumn(name="idorder")
	private Order order;
	
	private float amount;
	
	@Column(name="active", insertable = false)
	private Boolean active;
	
	@Column(name="lastrejection", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastrejection;
	
	public Alert() {
		
	}
	
	public Alert(Insolventuser insolventUser, float amount, Order order) {
		this.setInsolventUser(insolventUser);
		this.amount = amount;
		this.order = order;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public Boolean isActive() {
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Insolventuser getInsolventUser() {
		return insolventUser;
	}

	public void setInsolventUser(Insolventuser insolventUser) {
		this.insolventUser = insolventUser;
	}
}