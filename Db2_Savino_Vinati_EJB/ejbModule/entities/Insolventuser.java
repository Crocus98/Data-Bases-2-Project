package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;



@Entity
@Table(name = "insolventuser", schema="db2_savino_vinati")
@NamedQuery(name="Insolventuser.findAll", query="SELECT iu FROM Insolventuser iu")
public class Insolventuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	private int failedpaymentcount;

	private boolean insolvent;
	
	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	private User user;
	
	@OneToMany(mappedBy="insolventUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Alert> alerts;

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

	public List<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
	
	public void addAlert(Alert alert) {
		this.alerts.add(alert);
		alert.setInsolventUser(this);
	}
	
	public void updateToInactiveAlert(Order order) {
		for (int i = 0; i < this.getAlerts().size(); i++) {
			if(this.getAlerts().get(i).getOrder().getId() == order.getId()) {
				this.alerts.get(i).setActive(false);
			}
		}
	}

}