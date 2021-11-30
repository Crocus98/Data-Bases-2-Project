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
	
	@OneToMany(mappedBy="activationschedule")
	private List<Productschedule> productschedules;
	
	@OneToMany(mappedBy="activationschedule")
	private List<Serviceschedule> serviceschedules;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="productschedule",
			joinColumns= { @JoinColumn(name="idactivation")},
			inverseJoinColumns= { @JoinColumn(name="idoptionalproduct")}
	)
	private List<Optionalproduct> optionalproducts;
	
	

	public Activationschedule() {
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

	public List<Productschedule> getProductschedules() {
		return productschedules;
	}

	public void setProductschedules(List<Productschedule> productschedules) {
		this.productschedules = productschedules;
	}

	public List<Serviceschedule> getServiceschedules() {
		return serviceschedules;
	}

	public void setServiceschedules(List<Serviceschedule> serviceschedules) {
		this.serviceschedules = serviceschedules;
	}


}