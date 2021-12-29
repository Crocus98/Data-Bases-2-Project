package entities;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "activationschedule", schema="db2_savino_vinati")
@NamedQuery(name="Activationschedule.findAll", query="SELECT acsc FROM Activationschedule acsc")
public class Activationschedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Date activationdate;
	
	@Temporal(TemporalType.DATE)
	private Date deactivationdate;
	
	@ManyToOne
	@JoinColumn(name="iduser")
	private User user;

	
	@ManyToMany
	@JoinTable(
			name="serviceschedule",
			joinColumns= { @JoinColumn(name="idactivation")},
			inverseJoinColumns= { @JoinColumn(name="idservice")}
	)
	private List<Service> services;
	
	@ManyToMany
	@JoinTable(
			name="productschedule",
			joinColumns= { @JoinColumn(name="idactivation")},
			inverseJoinColumns= { @JoinColumn(name="idoptionalproduct")}
	)
	private List<Optionalproduct> optionalproducts;
	
	public Activationschedule() {
	}
	
	public Activationschedule(List<Service> services, List<Optionalproduct> optionalProducts, Date activationDate, Date deactivationDate, User user) {
		this.setServices(services);
		this.setOptionalproducts(optionalProducts);
		this.setActivationdate(activationDate);
		this.setDeactivationdate(deactivationDate);
		this.setUser(user);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getActivationdate() {
		return activationdate;
	}

	public void setActivationdate(Date activationdate) {
		this.activationdate = activationdate;
	}

	public Date getDeactivationdate() {
		return deactivationdate;
	}

	public void setDeactivationdate(Date deactivationdate) {
		this.deactivationdate = deactivationdate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<Optionalproduct> getOptionalproducts() {
		return optionalproducts;
	}

	public void setOptionalproducts(List<Optionalproduct> optionalproducts) {
		this.optionalproducts = optionalproducts;
	}

}