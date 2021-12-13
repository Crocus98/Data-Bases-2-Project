package entities;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name = "order", schema="db2_savino_vinati")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float totalvalue;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datehour;
	
	@Temporal(TemporalType.DATE)
	private Date startdate;
	
	private boolean paid;
	
	@ManyToOne
	@JoinColumn(name="iduser")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="idservicepackage")
	private Servicepackage servicepackage;
	
	@ManyToOne
	@JoinColumn(name="idvalidityperiod")
	private Validityperiod validityperiod;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="orderproduct",
			joinColumns= { @JoinColumn(name="idorder")},
			inverseJoinColumns= { @JoinColumn(name="idoptionalproduct")}
	)
	private List<Optionalproduct> optionalproducts;

	public Order() {
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public float getTotalvalue() {
		return totalvalue;
	}

	public void setTotalvalue(float totalvalue) {
		this.totalvalue = totalvalue;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Servicepackage getServicepackage() {
		return servicepackage;
	}

	public void setServicepackage(Servicepackage servicepackage) {
		this.servicepackage = servicepackage;
	}

	public Date getDatehour() {
		return datehour;
	}

	public void setDatehour(Date datehour) {
		this.datehour = datehour;
	}

	public Validityperiod getValidityperiod() {
		return validityperiod;
	}

	public void setValidityperiod(Validityperiod validityperiod) {
		this.validityperiod = validityperiod;
	}

	public List<Optionalproduct> getOptionalproducts() {
		return optionalproducts;
	}

	public void setOptionalproducts(List<Optionalproduct> optionalproducts) {
		this.optionalproducts = optionalproducts;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public void addOptionalProduct(Optionalproduct optionalproduct) {
		getOptionalproducts().add(optionalproduct);
		//is it necessary to update other side?
		// aligns both sides of the relationship
		// if mission is new, invoking persist() on reporter cascades also to mission
	}
	
	
}
